package com.redtourism.demo.service.impl;

import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.dao.ActivityInfoMapper;
import com.redtourism.demo.dao.ActivityMapper;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.ActivityInfo;
import com.redtourism.demo.service.IActivityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2021-1-16 - 21:22
 * Created by Salmon
 */
@Service("iActivityInfoService")
public class ActivityInfoServiceImpl implements IActivityInfoService {


    @Autowired(required = false)
    private ActivityInfoMapper activityInfoMapper;
    
    @Autowired(required = false)
    private ActivityMapper activityMapper;


    /**
     * 点赞和取消点赞
     * @param userId
     * @param activityId
     * @return
     */
    @Override
    public ServerResponse Point(Integer userId, Integer activityId){
        if (activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        ActivityInfo activityInfo = activityInfoMapper.selectByUserIdActivityId(userId, activityId);
        if(activityInfo == null){
            //此用户没有对此活动点赞或评论
            ActivityInfo activityInfonew = new ActivityInfo();
            activityInfonew.setAid(activityId);
            activityInfonew.setUserId(userId);
            activityInfonew.setpoint(1);
            activityInfoMapper.insert(activityInfonew);
        }else{
            //如果点赞了那就取消
            if(activityInfo.getpoint() == 1) {
                activityInfoMapper.cancelPointByUserIdActivityId(userId, activityId);
                return ServerResponse.createBySuccess("您已取消点赞","cancel");
            }else {
                //只评论没点赞，那就点赞
                activityInfoMapper.pointByUserIdActivityId(userId, activityId);
            }
        }
    
        int pointCount = activityInfoMapper.selectPointCountByActivityId(activityId);
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        activity.setActivityPoint(pointCount);
        activityMapper.updateByPrimaryKeySelective(activity);
        return ServerResponse.createBySuccess("您已成功点赞","success");
    }

    /**
     * 获取点赞数量
     * @param activityId
     * @return
     */
    @Override
    public ServerResponse pointCount(Integer activityId){
        if (activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int pointCount = activityInfoMapper.selectPointCountByActivityId(activityId);

        return ServerResponse.createBySuccess(pointCount);
    }
    
    /**
     * 保存足迹心得
     * @param content
     * @param aId
     * @param userId
     * @return
     */
    @Override
    public ServerResponse activityExperience(String content, Integer aId, Integer userId) {
        ActivityInfo activityInfo = activityInfoMapper.selectByUserIdActivityId(userId, aId);
        if (activityInfo == null) {
            //此用户没有对此活动点赞或评论
            ActivityInfo activityInfoNew = new ActivityInfo();
            activityInfoNew.setAid(aId);
            activityInfoNew.setUserId(userId);
            activityInfoNew.setContent(content);
            activityInfoNew.setpoint(1);
            activityInfoMapper.insert(activityInfoNew);
        } else {
            //如果点赞了那就取消
            activityInfo.setContent(content);
            activityInfoMapper.updateByPrimaryKeySelective(activityInfo);
        }
    
        int pointCount = activityInfoMapper.selectPointCountByActivityId(aId);
        Activity activity = activityMapper.selectByPrimaryKey(aId);
        activity.setActivityPoint(pointCount);
        activityMapper.updateByPrimaryKeySelective(activity);
        return ServerResponse.createBySuccessMessage("保存成功");
    }
    
    
    /**
     * 查看足迹心得
     * @param aId
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<ActivityInfo> getActivityExperience(Integer aId,Integer userId) {
        ActivityInfo activityInfo = activityInfoMapper.selectByUserIdActivityId(userId, aId);
        return ServerResponse.createBySuccess(activityInfo);
    }

    @Override
    public List<Activity> getRecordList(Integer userId) {

        List<Integer> actList = activityInfoMapper.getActList(userId);
        List<Activity> activities = new ArrayList<>();
        for (Integer act : actList) {
            Activity activity = activityMapper.selectByPrimaryKey(act);
            activities.add(activity);
        }
        return activities;
    }

    @Override
    public ActivityInfo editActLog(Integer aid, Integer userId) {
        return activityInfoMapper.editActLog(aid, userId);
    }


}
