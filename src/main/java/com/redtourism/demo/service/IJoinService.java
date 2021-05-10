package com.redtourism.demo.service;

import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.ActivityJoin;
import com.redtourism.demo.vo.ActivityJoinVo;
import com.redtourism.demo.vo.JoinPeople;

import java.util.List;
import java.util.Map;

/**
 * @date 2021-1-16 - 16:45
 * Created by Salmon
 */
public interface IJoinService {
	
	ServerResponse selectActivityPeople(Integer aId, Integer userId);
	
	List<JoinPeople> selectActivitySmallPeople(Integer aId);
	
	ActivityJoin selectJoinStatus(Integer aId, Integer userId);
	
	ServerResponse  updateJoinStatus(Integer aId, Integer joinId, Integer status, Integer userId);
	
	ServerResponse<ActivityJoinVo> add(Integer userId, Integer activityId);

    ServerResponse addActivity(Integer userId,Integer activityId);

    ServerResponse quitActivity(Integer userId,Integer activityId);

	List<Map<String,Object>> queryJoinActivityUsers(Integer aid);

	List<Activity> getActs(Integer userId);

}
