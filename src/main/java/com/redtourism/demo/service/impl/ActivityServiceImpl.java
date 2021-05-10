package com.redtourism.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.redtourism.demo.common.Const;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.dao.*;
import com.redtourism.demo.pojo.*;
import com.redtourism.demo.service.IActivityService;
import com.redtourism.demo.service.ICommentService;
import com.redtourism.demo.service.IJoinService;
import com.redtourism.demo.service.UserInfoService;
import com.redtourism.demo.util.DateTimeUtil;
import com.redtourism.demo.util.PropertiesUtil;
import com.redtourism.demo.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.RollbackException;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date 2021-1-15 - 17:54
 * Created by Salmon
 */
@Service("iActivityService")
public class ActivityServiceImpl implements IActivityService {

    @Autowired(required = false)
    private ActivityMapper activityMapper;
    
    @Autowired(required = false)
    private UserMapper userMapper;
    
    @Autowired
    private UserInfoService userInfoService;

    @Autowired(required = false)
    private ActivityJoinMapper joinMapper;
    
    @Autowired(required = false)
    private ActivityInfoMapper activityInfoMapper;
    
    @Autowired
    private IJoinService joinService;
    
    @Autowired
    private ICommentService commentService;
    /**
     * 发布或更新活动
     * @param activity
     * @return
     */
    @Override
    @Transactional(rollbackFor ={Exception.class})
    public ServerResponse saveOrUpdateActivity(Activity activity){
        if(activity != null){
            if(activity.getId() != null){
                int rowCount = activityMapper.updateByPrimaryKey(activity);
                if(rowCount > 0){
                    return ServerResponse.createBySuccessMessage("活动更新成功");
                }
                return ServerResponse.createByErrorMessage("更新活动失败");
            }else {
                int rowCount = activityMapper.insert(activity);
                if(rowCount > 0 && activity.getActivityPeople() > activity.getJoinpeople()) {
                    return ServerResponse.createBySuccessMessage("创建活动成功");
                }
                return ServerResponse.createByErrorMessage("创建活动失败");
            }
        }

        return ServerResponse.createByErrorMessage("活动创建不正确");
    }

    /**
     * 以活动id为依据修改活动状态
     * @param activityId
     * @param status
     * @return
     */
    @Override
    public ServerResponse<String> setActivityStatus(Integer activityId,Integer status,Integer userId){
        if(activityId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        if(!userId.equals(activity.getUserId())){
            return ServerResponse.createByErrorMessage("非活动创建人不能修改活动状态");
        }
        activity.setId(activityId);
        activity.setActivityStatus(status);
        int rowCount = activityMapper.updateByPrimaryKeySelective(activity);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("修改活动状态成功");
        }
        return ServerResponse.createByErrorMessage("修改活动状态失败");
    }


    /**
     * 获取活动详情
     * @param activityId
     * @return
     */
    @Override
    public ServerResponse<PublishUserInfoVo> getActivityDetail(Integer activityId, User user){
        if(activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        user = userMapper.selectByPrimaryKey(activity.getUserId());

        if(activity == null||activity.getActivityStatus() != Const.ActivityStatusEnum.ON_PROCEED.getCode()){
            return ServerResponse.createByErrorMessage("活动已取消或删除");
        }
        PublishUserInfoVo publishUserInfoVo = assembleActivityDetailVo(activity,user);
        return ServerResponse.createBySuccess(publishUserInfoVo);
    }

    /**
     * 为正上方getActivityDetail方法服务
     * @param activity
     * @return
     */
    private PublishUserInfoVo assembleActivityDetailVo(Activity activity, User user){
        UserInfo userInfo = userInfoService.queryUserInfoByUserId(user.getId());
        PublishUserInfoVo publishUserInfoVo = new PublishUserInfoVo();
        publishUserInfoVo.setMainPicture(activity.getMainPicture());
        publishUserInfoVo.setUserPhoto(userInfo.getPhoto());
        publishUserInfoVo.setStartTime(activity.getCreateTime());
        publishUserInfoVo.setEndTime(activity.getUpdateTime());
        publishUserInfoVo.setUserName(userInfo.getUsername());
        publishUserInfoVo.setPhone(user.getPhone());
        publishUserInfoVo.setJoinPeople(activity.getJoinpeople());
        publishUserInfoVo.setActivityPeople(activity.getActivityPeople());
        publishUserInfoVo.setOrganization(userInfo.getOrganization());
        publishUserInfoVo.setActivityAddress(activity.getActivityAddress());
        publishUserInfoVo.setActivityTime(activity.getActivityTime());
        publishUserInfoVo.setActivityTitle(activity.getActivityTitle());
        publishUserInfoVo.setActivityStatus(activity.getActivityStatus());
        publishUserInfoVo.setActivityContent(activity.getActivityContent());
        publishUserInfoVo.setActivityType(activity.getActivityType());
        publishUserInfoVo.setActivityPoint(activity.getActivityPoint());
        
    
        List<JoinPeople> joinPeopleList = joinService.selectActivitySmallPeople(activity.getId());
        publishUserInfoVo.setJoinPeopleInfo(joinPeopleList);
        
        // 为空就是未加入
        ActivityJoin activityJoin = joinService.selectJoinStatus(activity.getId(),user.getId());
        if(activityJoin!=null){
            publishUserInfoVo.setJoinStatus(activityJoin.getChecked());
        }
        ActivityInfo activityInfo = activityInfoMapper.selectByUserIdActivityId(user.getId(),activity.getId());
        Boolean isPoint = false;
        if(activityInfo!=null){
            if(activityInfo.getpoint()!=null&&activityInfo.getpoint()==1){
                isPoint=true;
            }
        }
        publishUserInfoVo.setIsPoint(isPoint);
        
        // 评论区
        List<Comment> commentList = commentService.selectComment(activity.getId());
        publishUserInfoVo.setComment(commentList);
        
        return publishUserInfoVo;
    }

    /**
     * 活动分页列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse getActivityList(int pageNum,int pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<Activity> activityList = activityMapper.selectList();


        List<ActivityListVo> activityListVoList = Lists.newArrayList();
        for (Activity activityItem : activityList){
            ActivityListVo activityListVo = assembleActivityListVo(activityItem);
            activityListVoList.add(activityListVo);
        }
        PageInfo pageResult = new PageInfo(activityList);
        pageResult.setList(activityListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    private ActivityListVo assembleActivityListVo(Activity activity){
        ActivityListVo activityListVo = new ActivityListVo();
        activityListVo.setAid(activity.getId());
        activityListVo.setMainPicture(activity.getMainPicture());
        activityListVo.setActivityPeople(activity.getActivityPeople());
        activityListVo.setActivityTitle(activity.getActivityTitle());
//        activityListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happyrmmall.top/"));
        activityListVo.setImageHost(activity.getMainPicture());
        activityListVo.setActivityType(activity.getActivityType());
        activityListVo.setActivityAddress(activity.getActivityAddress());
        activityListVo.setActivityStatus(activity.getActivityStatus());

        activityListVo.setJoinPeople(activity.getJoinpeople());

        return activityListVo;
    }

    /**
     * 模糊查询以及分类查询
     * @param keyword
     * @param type
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getActivityByKeywordType(String keyword,Integer type,int pageNum,int pageSize,String orderBy){
        if(StringUtils.isBlank(keyword) && type == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (type != null){
            List<Activity> activityList = activityMapper.selectByType(type);
            if(activityList == null && StringUtils.isBlank(keyword)){
                //没有该分类，且没有关键字，这时候返回一个空的结果集，不报错
                PageHelper.startPage(pageNum, pageSize);
                List<ActivityListVo> activityListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(activityListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }else {
                List<ActivityListVo> activityListVoList = Lists.newArrayList();
                for (Activity activity : activityList){
                    ActivityListVo activityListVo = assembleActivityListVo(activity);
                    activityListVoList.add(activityListVo);
                }
                PageInfo pageInfo = new PageInfo(activityList);
                pageInfo.setList(activityListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
        }
        if(StringUtils.isNotBlank(keyword)){
            //模糊查询
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        PageHelper.startPage(pageNum, pageSize);
        //排序处理
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.ActivityListOrderBy.PEOPLE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        //模糊查询名称及种类id
        List<Activity> activityList = activityMapper.selectByNameAndType(StringUtils.isBlank(keyword)?null:keyword, type == null?null:type);

        List<ActivityListVo> activityListVoList = Lists.newArrayList();
        for (Activity activity : activityList){
            ActivityListVo activityListVo = assembleActivityListVo(activity);
            activityListVoList.add(activityListVo);
        }

        PageInfo pageInfo = new PageInfo(activityList);
        pageInfo.setList(activityListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    
    /**
     * 我创建的活动
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    @Override
    public ServerResponse getActivityByCreat(int pageNum, int pageSize, Integer userId){
        PageHelper.startPage(pageNum,pageSize);
        
        List<Activity> activityList = activityMapper.selectList();
        
        List<ActivityListVo> activityListVoList = Lists.newArrayList();
        for (Activity activityItem : activityList){
            if(userId.equals(activityItem.getUserId())){
                ActivityListVo activityListVo = assembleActivityListVo(activityItem);
                activityListVoList.add(activityListVo);
            }
        }
        PageInfo pageResult = new PageInfo(activityList);
        pageResult.setList(activityListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }
    
    /**
     * 我加入的活动
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    @Override
    public ServerResponse getActivityByJoin(int pageNum, int pageSize, Integer userId){
        PageHelper.startPage(pageNum,pageSize);
        List<ActivityJoin> activityJoins = joinMapper.selectByUserId(userId);
        List<Integer> aIds = activityJoins.stream().map(ActivityJoin::getAid).collect(Collectors.toList());
        List<Activity> activityList = new ArrayList<>();
        if(!aIds.isEmpty()){
            Example example = new Example(Activity.class);
            example.createCriteria().andIn("id",aIds);
            activityList = activityMapper.selectByExample(example);
        }
        
        List<ActivityListVo> activityListVoList = Lists.newArrayList();
        for (Activity activityItem : activityList){
                ActivityListVo activityListVo = assembleActivityListVo(activityItem);
                activityListVoList.add(activityListVo);
        }
        PageInfo pageResult = new PageInfo(activityList);
        pageResult.setList(activityListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }







}
