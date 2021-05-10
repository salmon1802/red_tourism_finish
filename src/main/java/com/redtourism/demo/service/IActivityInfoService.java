package com.redtourism.demo.service;

import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.ActivityInfo;

import java.util.List;

/**
 * @date 2021-1-16 - 21:21
 * Created by Salmon
 */
public interface IActivityInfoService {

    ServerResponse Point(Integer userId, Integer activityId);

    ServerResponse pointCount(Integer activityId);
    
    ServerResponse activityExperience(String content, Integer aId, Integer userId);
    
    ServerResponse<ActivityInfo> getActivityExperience(Integer aId, Integer userId);

    List<Activity> getRecordList(Integer userId);

    ActivityInfo editActLog(Integer aid,Integer userId);
}
