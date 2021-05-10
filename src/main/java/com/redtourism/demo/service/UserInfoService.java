package com.redtourism.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.UserInfo;


import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-01-18 17:25:02
 */
public interface UserInfoService  {

    UserInfo queryUserInfoByUserId(int userId);
    
    ServerResponse updateAndInsert(UserInfo userInfo);
    
    void signInAddXinyonngfen(Integer userId);
}

