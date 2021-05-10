package com.redtourism.demo.controller.portal;


import com.redtourism.demo.common.Const;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.SignIn;
import com.redtourism.demo.pojo.User;
import com.redtourism.demo.service.ISignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/signIn/")
public class SignInController {
	@Autowired
	private ISignInService  signInService;
	
	/**
	 * 发布签到
	 */
	@PostMapping("/publishSign")
	@ResponseBody
	public ServerResponse publishSign(/*HttpSession session, */
			@RequestParam(value="userId") Integer userId ,
			@RequestParam(value="activityId") Integer activityId,
			@RequestParam(value="startTime") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date startTime,
			@RequestParam(value="endTime") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date endTime,
			@RequestParam(value="signName") String signName){
//		User user = (User)session.getAttribute(Const.CURRENT_USER);
//		if (user == null){
//			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
//		}
		return signInService.publishSignIn(userId, activityId,startTime,endTime,signName);
	}
	
	
	@RequestMapping("/getSignInList")
	@ResponseBody
	public ServerResponse getSignInList(/*HttpSession session, */Integer activityId,Integer userId){
//		User user = (User)session.getAttribute(Const.CURRENT_USER);
//		if (user == null){
//			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
//		}
		List<Map<String, Object>> signInList = signInService.getSignInListIncludeStatus(activityId, userId);
		return ServerResponse.createBySuccess(signInList);
	}
	
	
	@RequestMapping("/signInByUser")
	@ResponseBody
	public ServerResponse signInByUser(/*HttpSession session, */Integer signId,Integer userId){
//		User user = (User)session.getAttribute(Const.CURRENT_USER);
//		if (user == null){
//			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
//		}
		return signInService.signIn(signId,userId);
	}
	

}
