package com.redtourism.demo.dao;

import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.util.IMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityMapper extends IMapper<Activity> {
    int deleteByPrimaryKey(Integer aid);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer aid);
    int updateByPrimaryKeySelective(Activity record);
    int updateByPrimaryKey(Activity record);

    List<Activity> selectList();

    List<Activity> selectByType(Integer type);

    List<Activity> selectByNameAndType(@Param("activityTitle")String activityTitle, @Param("type")Integer type);

    int addJoinpeopleByPrimaryKey(Integer aid);

    int reduceJoinpeopleByPrimaryKey(Integer aid);

    List<Activity> selectByUserId(Integer userId);


}