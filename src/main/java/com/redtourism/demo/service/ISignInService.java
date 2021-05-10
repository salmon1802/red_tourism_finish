package com.redtourism.demo.service;


import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.SignIn;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-01-20 17:12:55
 */
public interface ISignInService  {
	
	ServerResponse publishSignIn(Integer userId, Integer aId, Date startTime, Date endTime, String signName);
	
	List<SignIn> getSignInByAId(Integer aId);

	List<Map<String, Object>> getSignInListIncludeStatus(Integer aId, Integer userId);
	
	ServerResponse signIn(Integer signInId, Integer userId);
}

