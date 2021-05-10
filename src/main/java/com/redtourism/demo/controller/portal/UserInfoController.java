package com.redtourism.demo.controller.portal;

import java.util.Arrays;


import com.baomidou.mybatisplus.extension.api.R;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.User;
import com.redtourism.demo.pojo.UserInfo;
import com.redtourism.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-01-18 17:25:02
 */
@RestController
@RequestMapping("/userinfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<UserInfo> list(int userId){
        UserInfo userInfo = userInfoService.queryUserInfoByUserId(userId);
        return ServerResponse.createBySuccess(userInfo);
    }

    /**
     * 单个用户信息
     */
    @RequestMapping(value = "/signal", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<UserInfo> signal(int userId){
        UserInfo userInfo = userInfoService.queryUserInfoByUserId(userId);
        return ServerResponse.createBySuccess(userInfo);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/updateOrInsert",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<UserInfo> lisupdateOrInsertt(@RequestBody UserInfo userInfo){
        return  userInfoService.updateAndInsert(userInfo);
       
    }
//
//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    @RequiresPermissions("sys:redtourismuserinfo:save")
//    public R save(@RequestBody RedTourismUserInfoEntity redTourismUserInfo){
//        userInfoService.save(redTourismUserInfo);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    @RequiresPermissions("sys:redtourismuserinfo:update")
//    public R update(@RequestBody RedTourismUserInfoEntity redTourismUserInfo){
//        ValidatorUtils.validateEntity(redTourismUserInfo);
//        userInfoService.updateById(redTourismUserInfo);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("sys:redtourismuserinfo:delete")
//    public R delete(@RequestBody Integer[] ids){
//        userInfoService.removeByIds(Arrays.asList(ids));
//
//        return R.ok();
//    }

}
