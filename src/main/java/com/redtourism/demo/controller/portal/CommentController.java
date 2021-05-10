package com.redtourism.demo.controller.portal;


import com.redtourism.demo.common.Const;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.Comment;
import com.redtourism.demo.pojo.User;
import com.redtourism.demo.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/comment/")
public class CommentController {
	
	@Autowired
	private ICommentService commentService;


	/**
	 * 获取评论
	 */
	@GetMapping("obtain")
	@ResponseBody
	public ServerResponse getPublish(Integer aid){
		try {
			return ServerResponse.createBySuccess("GetMessageSuccessfully",
					commentService.getPublish(aid));
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"GetMessageDefeat");
		}

	}


	
	/**
	 * 新增评论
	 */
	@PostMapping("publish")
	@ResponseBody
	public ServerResponse publish(@RequestBody Comment comment){
//		User user = (User)session.getAttribute(Const.CURRENT_USER);
//		if(user == null){
//			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
//		}else{
			
			return commentService.publishComment(comment.getAid(),comment.getUserId(),comment.getComment());
//		}
		
	}


	/**
	 * 删除评论
	 */
	@PostMapping("delete")
	@ResponseBody
	public ServerResponse deletePublish(@RequestBody Comment comment){
		try {
			int i = commentService.deletePublish(comment.getUserId(), comment.getAid(), comment.getMark());
			if (i==1){
				return ServerResponse.createBySuccess(ResponseCode.Success.getDesc(),i);
			}else {
				return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"DeleteMessageDefeat");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"DeleteMessageDefeat");
		}
	}
}
