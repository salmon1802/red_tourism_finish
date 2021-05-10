package com.redtourism.demo.service;

import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.User;

/**
 * @date 2021-1-15 - 17:05
 * Created by Salmon
 */
 public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse register(User user);

    ServerResponse<String> checkValid(String str,String type);

    ServerResponse selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String question,String answer);

    ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);


}
