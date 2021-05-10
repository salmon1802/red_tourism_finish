package com.redtourism.demo.service.impl;

import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.dao.ActivityMapper;
import com.redtourism.demo.dao.SignInMapper;
import com.redtourism.demo.dao.SignUserMapper;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.SignIn;
import com.redtourism.demo.pojo.SignUser;
import com.redtourism.demo.pojo.UserInfo;
import com.redtourism.demo.service.IActivityService;
import com.redtourism.demo.service.ISignInService;
import com.redtourism.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


@Service("redTourismSignService")
public class SignInServiceImpl implements ISignInService {
	
	@Autowired(required = false)
	private IActivityService activityService;
	@Autowired(required = false)
	private ActivityMapper activityMapper;
	@Autowired(required = false)
	private SignInMapper signInMapper;
	@Autowired(required = false)
	private SignUserMapper signUserMapper;
	@Autowired
	private UserInfoService userInfoService;
 
	@Override
	public ServerResponse publishSignIn(Integer userId, Integer aId, Date startTime, Date endTime, String signName){
		Activity activity = activityMapper.selectByPrimaryKey(aId);
		if(!userId.equals(activity.getUserId())){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"只有活动创建者才能发签到");
		}
		SignIn signIn = new SignIn();
		signIn.setAid(aId);
		signIn.setName(signName);
		signIn.setStartTime(startTime);
		signIn.setEndTime(endTime);
		signIn.setUseId(userId);
		signIn.setCreateTime(new Date());
		signInMapper.insertUseGeneratedKeys(signIn);
		
		List<SignIn> signInList = getSignInByAId(aId);
		return ServerResponse.createBySuccess(signInList);
	}
	
	@Override
	public List<SignIn> getSignInByAId(Integer aId) {
		Example example = new Example(SignIn.class);
		example.setOrderByClause("create_time DESC");
		example.createCriteria().andCondition("aId=",aId);
		return signInMapper.selectByExample(example);
	}
	
	@Override
	public List<Map<String, Object>> getSignInListIncludeStatus(Integer aId, Integer userId) {
//		List<SignIn> signIns = getSignInByAId(aId);
//		for(SignIn signIn : signIns){
//			if(!userId.equals(signIn.getUseId())){
//				SignUser signUser = getSignUserBySignIdAndUserId(userId, signIn.getId());
//				if(signUser==null){
//					signIn.setStatus(0);
//				}else {
//					signIn.setStatus(signUser.getStatus());
//				}
//			}
//		}
		// 获取最近一次发布签到的信息
		SignIn signIn = getSignInByAId(aId).get(0);
		// 获取最近一次签到Id
		Integer signId = signIn.getId();
		// 获取 发布签到者的姓名、发布时间
		UserInfo publishSignUserInfo = userInfoService.queryUserInfoByUserId(signIn.getUseId());
		String signUserPhoto = publishSignUserInfo.getPhoto();
		String publishSignUsername = publishSignUserInfo.getUsername();
		Date signInCreateTime = signIn.getCreateTime();
		// 通过最近一次签到Id从 red_tourism_sign_user
		// 和red_tourism_user_info表中获取所有签到的用户信息
		// 并封装返回给前端列表数据【用户头像、用户姓名、签到时间】
		// FIXME  待优化 用多表查询解决用户信息问题，不能在for循环中查询数据库
		Example example = new Example(SignUser.class);
		example.createCriteria().andCondition("sign_id=",signId);
 		List<SignUser> signUsers = signUserMapper.selectByExample(example);

		List<Map<String, Object>> lastSignUserInfos = new ArrayList<>();
		// 如果 还没有人签到，返回数组，数组中只有发布者信息,
		// 有人签到则先存入发布者信息，在存入签到者信息
		// 1. 存入发布者信息
		Map<String, Object> publishSignUser = new HashMap<>();
		publishSignUser.put("photo",signUserPhoto);
		publishSignUser.put("userName",publishSignUsername);
		publishSignUser.put("signTime",signInCreateTime);
		publishSignUser.put("signId",signId);
		lastSignUserInfos.add(publishSignUser);

		if (signUsers.size()!=0) { // 表示有人签到
			// 2. 存入签到者信息
			for (SignUser signUser : signUsers) {
				Map<String, Object> lastSignUserInfo = new HashMap<>();
				UserInfo userInfo = userInfoService.queryUserInfoByUserId(signUser.getUserId());
				lastSignUserInfo.put("photo",userInfo.getPhoto());
				lastSignUserInfo.put("userName",userInfo.getUsername());
				lastSignUserInfo.put("signTime",signUser.getSignTime());
				lastSignUserInfo.put("signId",signUser.getSignId());
				lastSignUserInfos.add(lastSignUserInfo);
			}
		}
		// 最后返回签到列表信息
		return lastSignUserInfos;
	}
	
	private SignUser getSignUserBySignIdAndUserId(Integer userId,  Integer signInId) {
		Example example = new Example(SignUser.class);
		example.createCriteria().andCondition("sign_id=",signInId).andCondition("user_id=",userId);
		return signUserMapper.selectOneByExample(example);
	}
	
	
	@Override
	public  ServerResponse signIn(Integer signInId,Integer userId){
		SignIn signIn = signInMapper.selectByPrimaryKey(signInId);
		SignUser signUser = getSignUserBySignIdAndUserId(userId,signInId);
		Date currentDate  = new Date();
		if(currentDate.before(signIn.getStartTime())){
			return ServerResponse.createByErrorMessage("签到未开始");
		} else if(currentDate.after(signIn.getEndTime())){
//			if(signUser!=null){
//				signUser.setStatus(2);
//				signUserMapper.updateByPrimaryKeySelective(signUser);
//			}else {
//				newSignInUser(signInId, userId, signIn,2);
//			}
			return ServerResponse.createBySuccess("签到已过期",getSignInListIncludeStatus(signIn.getAid(),userId));
		}
		if(signUser!=null){
//			if(signUser.getStatus().equals(1)){
				return ServerResponse.createByErrorMessage("您已签到");
//			}
//			signUser.setStatus(1);
//			signUser.setSignTime(new Date());
//			signUserMapper.updateByPrimaryKeySelective(signUser);
		}else {
			newSignInUser(signInId, userId, signIn);
		}
		//加分
		userInfoService.signInAddXinyonngfen(userId);
		return ServerResponse.createBySuccess("签到成功",getSignInListIncludeStatus(signIn.getAid(),userId));
		
	}
	
	private void newSignInUser(Integer signInId, Integer userId, SignIn signIn) {
		SignUser signUser1 = new SignUser();
		signUser1.setAid(signIn.getAid());
		signUser1.setSignId(signInId);
		signUser1.setStatus(1);
		signUser1.setUserId(userId);
		signUser1.setSignTime(new Date());
		signUserMapper.insertUseGeneratedKeys(signUser1);
	}
	
	
}
