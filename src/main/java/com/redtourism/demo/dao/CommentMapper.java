package com.redtourism.demo.dao;

import com.redtourism.demo.pojo.Comment;
import com.redtourism.demo.util.IMapper;

import java.util.List;


/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-01-20 17:12:55
 */

public interface CommentMapper extends IMapper<Comment> {

    List<Comment> selectByAid(Integer aid);

    int deleteByAidAndUserIdAndMark(Integer userId, Integer aid, String mark);
	
}
