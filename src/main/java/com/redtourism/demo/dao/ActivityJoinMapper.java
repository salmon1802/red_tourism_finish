package com.redtourism.demo.dao;

import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.ActivityJoin;
import com.redtourism.demo.util.IMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityJoinMapper extends IMapper<ActivityJoin> {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityJoin record);

    int insertSelective(ActivityJoin record);

    ActivityJoin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityJoin record);

    int updateByPrimaryKey(ActivityJoin record);

    ActivityJoin selectByUserIdActivityId(@Param("userId")Integer userId, @Param("activityId") Integer activityId);

    List<ActivityJoin> selectByUserId(Integer userId);

    int selectActivityCheckedStatusByUserId(Integer userId);

    List<ActivityJoin> queryJoinActivityUsers(Integer aid);
}