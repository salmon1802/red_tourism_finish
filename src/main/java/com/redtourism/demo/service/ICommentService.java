package com.redtourism.demo.service;



import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.Comment;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-01-20 17:12:55
 */
public interface  ICommentService  {
	
	
	ServerResponse publishComment(Integer activityId,Integer userId, String comment);
	
	List<Comment> selectComment(Integer aId);


	List<Comment> getPublish(Integer aId);

	int deletePublish(Integer userId, Integer aid, String mark);
}

