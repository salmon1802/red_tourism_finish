package com.redtourism.demo.dao;

import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.ActivityInfo;
import com.redtourism.demo.pojo.ActivityJoin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityInfo record);

    int insertSelective(ActivityInfo record);

    ActivityInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityInfo record);

    int updateByPrimaryKey(ActivityInfo record);

    List<ActivityInfo> selectContentByActivityId(Integer aid);

    ActivityInfo selectByUserIdActivityId(@Param("userId")Integer userId, @Param("activityId") Integer activityId);

    int pointByUserIdActivityId(@Param("userId")Integer userId, @Param("activityId") Integer activityId);

    int cancelPointByUserIdActivityId(@Param("userId")Integer userId, @Param("activityId") Integer activityId);

    int selectPointCountByActivityId(Integer activityId);

    List<Integer> getActList(Integer userId);

    ActivityInfo editActLog(Integer aid, Integer userId);




}