package com.redtourism.demo.service.impl;


import com.google.common.collect.Lists;
import com.redtourism.demo.common.Const;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.dao.ActivityJoinMapper;
import com.redtourism.demo.dao.ActivityMapper;
import com.redtourism.demo.dao.UserInfoMapper;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.ActivityJoin;
import com.redtourism.demo.pojo.User;
import com.redtourism.demo.pojo.UserInfo;
import com.redtourism.demo.service.IJoinService;
import com.redtourism.demo.util.PropertiesUtil;
import com.redtourism.demo.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2021-1-16 - 16:45
 * Created by Salmon
 */
@Service("iJoinService")
public class JoinServiceImpl implements IJoinService {

    @Autowired(required = false)
    private ActivityJoinMapper activityJoinMapper;

    @Autowired(required = false)
    private ActivityMapper activityMapper;
    
    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    
    @Autowired(required = false)
    private UserInfoServiceImpl userInfoService;

    /**
     * 用户直接加入活动OK
     * @param userId
     * @param activityId
     * @return
     */
    @Override
    public ServerResponse addActivity(Integer userId,Integer activityId){
        if (activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        ActivityJoin activityJoin = activityJoinMapper.selectByUserIdActivityId(userId, activityId);
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        if(userId.equals(activity.getUserId())){
            return ServerResponse.createByErrorMessage("您是活动创建人");
        }

        if(activityJoin == null){
            if(activity.getActivityPeople() - activity.getJoinpeople() < 0){
                return ServerResponse.createByErrorMessage("此活动报名人数已满");
            }else {
                ActivityJoin activityJoinItem = new ActivityJoin();
                activityJoinItem.setAid(activityId);
                activityJoinItem.setQuantity(activity.getActivityPeople() - activity.getJoinpeople());
                //未同意设置为0
                activityJoinItem.setChecked(0);
                activityJoinItem.setUserId(userId);
                activityJoinItem.setCreateTime(new Date());
                activityJoinMapper.insert(activityJoinItem);
                //更新可加入的活动人数
                activityMapper.addJoinpeopleByPrimaryKey(activityId);
            }
            Integer joinPeopleNum = updateJoinPeopleNum(activityId);
            return ServerResponse.createBySuccess("您已成功加入此活动",joinPeopleNum);

        }else{
            return ServerResponse.createByErrorMessage("您已经加入过此活动，请不要重复加入");
        }


    }

    /**
     * 退出当前活动OK
     * @param userId
     * @param activityId
     * @return
     */
    @Override
    public ServerResponse quitActivity(Integer userId,Integer activityId){
        if (activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        ActivityJoin activityJoin = activityJoinMapper.selectByUserIdActivityId(userId, activityId);
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        if(userId.equals(activity.getUserId())){
            return ServerResponse.createByErrorMessage("您是活动创建人");
        
        }

        if(activityJoin != null){
                activityJoinMapper.deleteByPrimaryKey(activityJoin.getId());
                //更新可加入的活动人数
                activityMapper.reduceJoinpeopleByPrimaryKey(activityId);
                updateJoinPeopleNum(activityId);
        }else{
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
        }
        return ServerResponse.createBySuccessMessage("您已成功退出此活动");
    }

    @Override
    public List<Map<String,Object>> queryJoinActivityUsers(Integer aid) {
        List<Map<String,Object>> mapList = new ArrayList<>();
        List<ActivityJoin> activityJoins = activityJoinMapper.queryJoinActivityUsers(aid);
        for (ActivityJoin activityJoin : activityJoins) {
            Map<String,Object> mapper = new HashMap<>();
            Integer joinId = activityJoin.getId();
            Integer checked = activityJoin.getChecked();
            Integer activityJoinAid = activityJoin.getAid();
            String username = userInfoService.queryUserInfoByUserId(activityJoin.getUserId()).getUsername();
            mapper.put("joinId",joinId);
            mapper.put("checked",checked);
            mapper.put("activityId",activityJoinAid);
            mapper.put("userId",activityJoin.getUserId());
            mapper.put("username",username);
            mapList.add(mapper);
        }
        return mapList;
    }


    private Integer updateJoinPeopleNum(Integer aId){
        List<Integer> statusList = new ArrayList<>(Arrays.asList(0,1));
        Example example = new Example(ActivityJoin.class);
       example.createCriteria().andCondition("aid=",aId)/*.andIn("status",statusList)*/;
        List<ActivityJoin> activityJoins = activityJoinMapper.selectByExample(example);
        Integer joinpeopleNum = activityJoins.size();
        Activity activity =activityMapper.selectByPrimaryKey(aId);
        activity.setJoinpeople(joinpeopleNum);
        activityMapper.updateByPrimaryKeySelective(activity);
        return joinpeopleNum;
    }
    
    /**
     * 活动下的成员详情页通用版
     * @param aId
     * @param userId
     * @return
     */
    
    @Override
    public ServerResponse selectActivityPeople(Integer aId, Integer userId){
        Activity activity = activityMapper.selectByPrimaryKey(aId);
        JoinPeopleInfoList joinPeopleInfoList = new JoinPeopleInfoList();
        UserInfo captainInfo =  userInfoService.queryUserInfoByUserId(userId);
        List<JoinPeopleInfo> joinPeopleInfos = new ArrayList<>();
        if(userId.equals(activity.getUserId())){
            setCaptainInfo(activity, joinPeopleInfoList, captainInfo,true);
            
            Example example = new Example(ActivityJoin.class);
            example.createCriteria().andCondition("aid=",aId);
            List<ActivityJoin> activityJoins = activityJoinMapper.selectByExample(example);
    
            setJoinPeopleInfo(joinPeopleInfos, activityJoins);
    
        }else{
            setCaptainInfo(activity, joinPeopleInfoList, captainInfo,false);
            Example example = new Example(ActivityJoin.class);
            example.createCriteria().andCondition("aid=",aId).andCondition("checked=",1);
            List<ActivityJoin> activityJoins = activityJoinMapper.selectByExample(example);
            setJoinPeopleInfo(joinPeopleInfos, activityJoins);
        }
        joinPeopleInfoList.setJoinPeopleInfos(joinPeopleInfos);
        return  ServerResponse.createBySuccess(joinPeopleInfoList);
        
    }
    
    private void setJoinPeopleInfo(List<JoinPeopleInfo> joinPeopleInfos, List<ActivityJoin> activityJoins) {
        for(ActivityJoin activityJoin :activityJoins){
            UserInfo joinUserInfo = userInfoService.queryUserInfoByUserId(activityJoin.getUserId());
            JoinPeopleInfo joinPeopleInfo = new JoinPeopleInfo();
            joinPeopleInfo.setUseInfoId(joinUserInfo.getId());
            joinPeopleInfo.setAId(activityJoin.getAid());
            joinPeopleInfo.setJoinId(activityJoin.getId());
            joinPeopleInfo.setUsername(joinUserInfo.getUsername());
            joinPeopleInfo.setPhoto(joinUserInfo.getPhoto());
            joinPeopleInfo.setIsCaptain(false);
            joinPeopleInfo.setJoinStatus(activityJoin.getChecked());
            joinPeopleInfo.setJoinTime(activityJoin.getCreateTime());
            joinPeopleInfo.setUserId(joinUserInfo.getUserId());
            joinPeopleInfos.add(joinPeopleInfo);
        }
    }
    
    private void setCaptainInfo(Activity activity, JoinPeopleInfoList joinPeopleInfoList, UserInfo captainInfo,Boolean isCaptain) {
        joinPeopleInfoList.setIsCaptain(isCaptain);
        JoinPeopleInfo captainJoinInfo = new JoinPeopleInfo();
        captainJoinInfo.setUseInfoId(captainInfo.getId());
        captainJoinInfo.setAId(activity.getId());
        captainJoinInfo.setUserId(captainInfo.getUserId());
        captainJoinInfo.setJoinTime(activity.getCreateTime());
        captainJoinInfo.setIsCaptain(isCaptain);
        captainJoinInfo.setPhoto(captainInfo.getPhoto());
        captainJoinInfo.setUsername(captainInfo.getUsername());
        joinPeopleInfoList.setCaptainInfo(captainJoinInfo);
    }
    
    @Override
    public List<JoinPeople> selectActivitySmallPeople(Integer aId){
        Example example = new Example(ActivityJoin.class);
        example.createCriteria().andCondition("aid=",aId).andCondition("checked=",1);
        List<ActivityJoin> activityJoins = activityJoinMapper.selectByExample(example);
        List<JoinPeople> joinPeoples = new ArrayList<>();
        for(ActivityJoin activityJoin :activityJoins){
            UserInfo joinUserInfo = userInfoService.queryUserInfoByUserId(activityJoin.getUserId());
            JoinPeople joinPeople = new JoinPeople();
            joinPeople.setUserName(joinUserInfo.getUsername());
            joinPeople.setUserId(joinUserInfo.getUserId());
            joinPeople.setPhoto(joinUserInfo.getPhoto());
            joinPeoples.add(joinPeople);
        }
        return joinPeoples;
    }
    
    @Override
    public ActivityJoin selectJoinStatus(Integer aId, Integer userId){
        Example example = new Example(ActivityJoin.class);
        example.createCriteria().andCondition("user_id =",userId).andCondition("aid=",aId);
        return activityJoinMapper.selectOneByExample(example);
    }
    
    @Override
    public ServerResponse updateJoinStatus(Integer aId, Integer joinId , Integer status, Integer userId){
        if(!status.equals(1)&&!status.equals(2)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"状态错误");
        }
        Activity activity = activityMapper.selectByPrimaryKey(aId);
        if(!userId.equals(activity.getUserId())){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"非活动发布者");
        }
        ActivityJoin activityJoin = activityJoinMapper.selectByPrimaryKey(joinId);
        UserInfo joinUserInfo =  userInfoService.queryUserInfoByUserId(activityJoin.getUserId());
        if(status.equals(2)){
            if(!status.equals(activityJoin.getChecked())){
                activityMapper.reduceJoinpeopleByPrimaryKey(aId);
                activityJoin.setChecked(status);
            }
        }
        if(status.equals(1)){
            if(!status.equals(activityJoin.getChecked())){
                activityJoin.setChecked(status);
                //添加工艺时间
                joinUserInfo.setGongyiTime(activity.getActivityTime());
                
            }
        }
        userInfoMapper.updateByPrimaryKey(joinUserInfo);
        activityJoinMapper.updateByPrimaryKey(activityJoin);
        updateJoinPeopleNum(aId);
        return ServerResponse.createBySuccess(ResponseCode.Success.getCode());
    }



    @Override
    public List<Activity> getActs(Integer userId) {
        List<Activity> activities = new ArrayList<>();
        // 获取自己创建的活动
        List<Activity> activityList = activityMapper.selectByUserId(userId);
        activities.addAll(activityList);
        // 获取加入的活动
        List<ActivityJoin> activityJoins = activityJoinMapper.selectByUserId(userId);
        for (ActivityJoin activityJoin : activityJoins) {
            Activity activity = activityMapper.selectByPrimaryKey(activityJoin.getAid());
            activities.add(activity);
        }
        return activities;
    }
    


































    /**
     * -------------------------------------------------------
     * 等开发到收藏夹再使用下面的代码
     */

    /**
     * 用户根据收藏活动中的活动id加入该活动
     * @param userId
     * @param activityId
     * @return
     */
    @Override
    public ServerResponse<ActivityJoinVo> add(Integer userId, Integer activityId){
        if (activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        ActivityJoin activityJoin = activityJoinMapper.selectByUserIdActivityId(userId, activityId);
        if(activityJoin == null){
            //当前用户没有加入过该活动，需要重新设置
            ActivityJoin activityJoinItem = new ActivityJoin();
            activityJoinItem.setChecked(Const.MyActivity.CHECKED); //设置活动被选中，此功能看心情
            activityJoinItem.setAid(activityId);
            activityJoinItem.setUserId(userId);
            activityJoinMapper.insert(activityJoinItem);
        }else{
            return ServerResponse.createByErrorMessage("您已经加入过此活动，请不要重复加入");
        }

        return this.list(userId);
    }





    /**
     * 展示收藏的活动列表
     * @param userId
     * @return
     */
    public ServerResponse<ActivityJoinVo> list (Integer userId){
        ActivityJoinVo activityJoinVo = this.getActivityJoinVoLimit(userId);
        if(activityJoinVo == null){
            return ServerResponse.createByErrorMessage("此活动不存在");
        }else {
            return ServerResponse.createBySuccess(activityJoinVo);
        }
    }

    private ActivityJoinVo getActivityJoinVoLimit(Integer userId){
        ActivityJoinVo activityJoinVo = new ActivityJoinVo();

        //获得所有的red_tourism_activity_join表信息
        List<ActivityJoin> activityJoinList = activityJoinMapper.selectByUserId(userId);
        List<ActivityJoinItemVo> activityJoinItemVoList = Lists.newArrayList();


        if(CollectionUtils.isNotEmpty(activityJoinList)){
            for(ActivityJoin activityJoinItem : activityJoinList){
                ActivityJoinItemVo activityJoinItemVo = new ActivityJoinItemVo();


                activityJoinItemVo.setId(activityJoinItem.getId());
                activityJoinItemVo.setUserId(userId);
                activityJoinItemVo.setAid(activityJoinItem.getAid());
                //获得所有的活动red_tourism_activity表信息
                Activity activity = activityMapper.selectByPrimaryKey(activityJoinItem.getAid());
                if(activity != null){
                    activityJoinItemVo.setMainPicture(activity.getMainPicture());
                    activityJoinItemVo.setActivityPeople(activity.getActivityPeople());
                    activityJoinItemVo.setActivityTitle(activity.getActivityTitle());
                    activityJoinItemVo.setActivityContent(activity.getActivityContent());
                    activityJoinItemVo.setActivityType(activity.getActivityType());
                    activityJoinItemVo.setActivityAddress(activity.getActivityAddress());
                    activityJoinItemVo.setActivityStatus(activity.getActivityStatus());
                    activityJoinItemVo.setJoinPeople(activity.getJoinpeople() + 1);
                    //判断当前活动是否满员
                    int LimitCount = 0;
                    if(activity.getActivityPeople() >= activity.getJoinpeople()){
                        //活动可参与人数充足的时候
                        LimitCount = activityJoinItem.getQuantity();
                        activityJoinItemVo.setLimitQuantity(Const.MyActivity.LIMIT_NUM_SUCCESS);
                    }else{
                        LimitCount = activity.getActivityPeople() - activity.getJoinpeople();
                        activityJoinItemVo.setLimitQuantity(Const.MyActivity.LIMIT_NUM_FAIL);
                        //收藏的活动中更新有效库存
                        ActivityJoin activityJoinForQuantity = new ActivityJoin();
                        activityJoinForQuantity.setId(activityJoinItem.getId());
                        activityJoinForQuantity.setQuantity(LimitCount);
                        activityJoinMapper.updateByPrimaryKeySelective(activityJoinForQuantity);
                    }
                    activityJoinItemVo.setQuantity(LimitCount);

                    activityJoinItemVo.setActivityChecked(activityJoinItem.getChecked());
                }

                activityJoinItemVoList.add(activityJoinItemVo);
            }
        }
        activityJoinVo.setActivityJoinItemVoList(activityJoinItemVoList);
        activityJoinVo.setAllChecked(this.getAllCheckedStatus(userId));
        activityJoinVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return activityJoinVo;
    }


    /**
     * 查看是否为全选
     * @param userId
     * @return
     */
    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }

        //此查询查询的是：当userId确定时未勾选的收藏列表中活动个数，所以为0时全选，不为0时不是全选
        return activityJoinMapper.selectActivityCheckedStatusByUserId(userId) == 0;
    }






}
