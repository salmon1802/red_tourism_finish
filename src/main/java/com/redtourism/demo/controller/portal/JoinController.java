package com.redtourism.demo.controller.portal;

import com.redtourism.demo.common.Const;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.ActivityJoin;
import com.redtourism.demo.pojo.User;
import com.redtourism.demo.service.IJoinService;
import com.redtourism.demo.vo.ActivityJoinVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @date 2021-1-16 - 16:34
 * Created by Salmon
 */
@Controller
@RequestMapping("/join/")
public class JoinController {


    @Autowired
    private IJoinService iJoinService;




    /**
     * 用户在首页直接加入活动
     * @param session
     * @param activityId
     * @return
     */
    @RequestMapping("add_activity.do")
    @ResponseBody
    public ServerResponse<ActivityJoinVo> addActivity(HttpSession session,Integer activityId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iJoinService.addActivity(user.getId(), activityId);
    }

    /**
     * 用户在首页直接退出活动
     * @param session
     * @param activityId
     * @return
     */
    @RequestMapping("quit_activity.do")
    @ResponseBody
    public ServerResponse<ActivityJoinVo> quitActivity(HttpSession session,Integer activityId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iJoinService.quitActivity(user.getId(), activityId);
    }
    
    
    /**
     * 获取活动参与人详细列表
     * @param session
     * @param activityId
     * @return
     */
 
    @RequestMapping("activityPeopleDetail")
    @ResponseBody
    public ServerResponse activityPeopleDetail(HttpSession session,Integer activityId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        
        return iJoinService.selectActivityPeople(activityId, user.getId());
    }
    
    
    /**
     * 允许或拒绝用户加入活动  status 0未通过 1允许 2拒绝
     * @param activityId
     * @param joinId
     * @param status
     * @return
     */
    @PostMapping("updateJoinStatus")
    @ResponseBody
    public ServerResponse updateJoinStatus(@RequestParam(value = "activityId") Integer activityId,
                                           @RequestParam(value = "joinId") Integer joinId,
                                           @RequestParam(value = "status") Integer status,
                                           @RequestParam(value = "userId") Integer userId){
        return iJoinService.updateJoinStatus(activityId, joinId, status, userId);
    }


    /**
     * @Description: 通过活动ID(aid)获取所有加入、待加入用户
     * @Params: aid
     * @Return
     */
    @GetMapping("list")
    @ResponseBody
    public ServerResponse showList(Integer aid){
        try {
            return ServerResponse.createBySuccess(iJoinService.queryJoinActivityUsers(aid));
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
        }
    }














    /**
     * 收藏夹功能我溜了
     */


    /**
     *
     * 用户通过关注列表参加活动
     * @param session
     * @param activityId
     * @return
     */
     @RequestMapping("add.do")
     @ResponseBody
     public ServerResponse<ActivityJoinVo> add(HttpSession session, Integer activityId){
     User user = (User)session.getAttribute(Const.CURRENT_USER);
     if (user == null){
     return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
     }
     return iJoinService.add(user.getId(), activityId);
     }


    @GetMapping(value = "actsByUserId")
    @ResponseBody
    public ServerResponse getActs(Integer userId){
        try {
            return ServerResponse.createBySuccess(ResponseCode.Success.getDesc(),iJoinService.getActs(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"服务异常！");
        }
    }


}
