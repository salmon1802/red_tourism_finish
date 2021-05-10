package com.redtourism.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.dao.UserInfoMapper;
import com.redtourism.demo.dao.UserMapper;
import com.redtourism.demo.pojo.User;
import com.redtourism.demo.pojo.UserInfo;
import com.redtourism.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Service("redTourismUserInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    @Autowired(required = false)
    private UserServiceImpl userService;
    
    @Override
    public UserInfo queryUserInfoByUserId(int userId) {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andCondition("user_id=",userId);
        UserInfo userInfo = userInfoMapper.selectOneByExample(example);
        return userInfo;
    }
    
    @Override
    public ServerResponse updateAndInsert(UserInfo userInfo){
        if(userInfo.getUserId()==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"获取用户Id失败");
        }
        UserInfo olduserInfo = queryUserInfoByUserId(userInfo.getUserId());
        if(/*userInfo.getId()==null&&*/olduserInfo==null){
            userInfo.setBirthday(new Date());
            userInfo.setGongyiTime(BigDecimal.valueOf(0));
            userInfo.setXinyongTime(12);
            userInfo.setCreateTime(new Date());
            userInfoMapper.insertUseGeneratedKeys(userInfo);
        }else {
            userInfo.setUpdateTime(new Date());
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }
        
        ServerResponse<User> serverResponseUser = userService.getInformation(userInfo.getUserId());
        if(serverResponseUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"获取用户Id失败");
        }
        User user = serverResponseUser.getData();
        user.setUsername(userInfo.getUsername());
        user.setUpdateTime(new Date());
        userService.updateInformation(user);
        return ServerResponse.createBySuccess(ResponseCode.Success.getDesc(),userInfo);

    }
    
    @Override
    public void signInAddXinyonngfen(Integer userId){
        UserInfo userInfo = queryUserInfoByUserId(userId);
        userInfo.setXinyongTime(userInfo.getXinyongTime()+1);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
        
    }
    
    
    
}
