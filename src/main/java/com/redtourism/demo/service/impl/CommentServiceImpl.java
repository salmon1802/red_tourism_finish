package com.redtourism.demo.service.impl;

import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.dao.CommentMapper;
import com.redtourism.demo.pojo.Comment;

import com.redtourism.demo.pojo.UserInfo;
import com.redtourism.demo.service.ICommentService;
import com.redtourism.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service("redTourismActivity commentService")
public class CommentServiceImpl implements ICommentService {
	
	@Autowired(required = false)
	private CommentMapper commentMapper;
	@Autowired(required = false)
	private UserInfoService userInfoService;
	
	
	@Override
	public ServerResponse publishComment(Integer activityId,Integer userId, String comment){
		UserInfo userInfo = userInfoService.queryUserInfoByUserId(userId);
		Comment com = new Comment();
		com.setAid(activityId);
		com.setComment(comment);
		String mark = userId.toString()+activityId.toString()+ UUID.randomUUID();
		com.setMark(mark.replace("-",""));
		com.setCreateTime(new Date());
		com.setUserId(userId);
		com.setUserName(userInfo.getUsername());
		com.setUserPhoto(userInfo.getPhoto());
		commentMapper.insertUseGeneratedKeys(com);
		return ServerResponse.createBySuccess(ResponseCode.Success.getDesc(),com);
	}
	
	@Override
	public List<Comment> selectComment(Integer aId){
		Example example = new Example(Comment.class);
		example.createCriteria().andCondition("aid=",aId);
		return commentMapper.selectByExample(example);
	}

	@Override
	public List<Comment> getPublish(Integer aid) {
		return commentMapper.selectByAid(aid);
	}

	@Override
	public int deletePublish(Integer userId, Integer aid, String mark) {
		return commentMapper.deleteByAidAndUserIdAndMark(userId, aid, mark);
	}

}
