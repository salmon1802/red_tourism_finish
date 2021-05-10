package com.redtourism.demo.controller.portal;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.redtourism.demo.common.Const;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.User;
import com.redtourism.demo.service.IActivityService;
import com.redtourism.demo.service.IFileService;
import com.redtourism.demo.service.IJoinService;
import com.redtourism.demo.service.IUserService;
import com.redtourism.demo.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @date 2021-1-15 - 17:50
 * Created by Salmon
 */
@Controller
@RequestMapping("/activity/")
public class ActivityController {

    @Autowired
    private IFileService iFileService;

    @Autowired
    private IActivityService iActivityService;

    @Autowired
    private IUserService iUserService;


    /**
     * 创建或更新活动OK
     * 活动状态(1代表进行，0代表结束)
     * @param session
     * @param activity
     * @return
     */
    @RequestMapping("save_activity.do")
    @ResponseBody
    public ServerResponse saveActivity(HttpSession session,@RequestBody Activity activity){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else{
            //增加产品的业务逻辑
            return iActivityService.saveOrUpdateActivity(activity);
        }
    }

    /**
     * 以活动id为依据设置活动状态OK
     * 活动状态(1代表进行，0代表结束)
     * @param session
     * @param activityId
     * @param status
     * @return
     */
    @RequestMapping("set_activity_status.do")
    @ResponseBody
    public ServerResponse setActivityStatus(HttpSession session, Integer activityId, Integer status,Integer userId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else{
            //增加产品的业务逻辑
            return iActivityService.setActivityStatus(activityId,status,userId);
        }
    }

    

    /**
     * 根据活动id获取活动详细信息OK
     * @param session
     * @param activityId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer activityId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else{
            //增加产品的业务逻辑
            return iActivityService.getActivityDetail(activityId,user);
        }
    }

    /**
     * 全部的活动分页列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value="list.do" , method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getList(int pageNum,int pageSize){

            return iActivityService.getActivityList(pageNum, pageSize);
    }

    /**
     * 模糊查询以及分类查询OK
     * 类别：（'1.党建旅游 2.研学旅游 3.红色旅游 4.娱乐旅游'）
     * 模糊查询：要求输入活动主题关键字
     * @param keyword
     * @param type
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping("type_fuzzy_list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "type", required = false) Integer type,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy){

        return iActivityService.getActivityByKeywordType(keyword, type, pageNum, pageSize, orderBy);
    }
    
    
    
    
    
    /**
     * 全部的活动分页列表
     * @param pageNum
     * @param pageSize/activity/detail.do
     * @return
     * 0代表创建 1代表加入
     */
    @RequestMapping("listByCreatOrJoin")
    @ResponseBody
    public ServerResponse listByCreatOrJoin(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,Integer userId,Integer code){
        if(!code.equals(0)&&!code.equals(1)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"状态码错误");
        }
        if(code.equals(0)){
            return iActivityService.getActivityByCreat(pageNum,pageSize,userId);
        }else{
            return iActivityService.getActivityByJoin(pageNum,pageSize,userId);
        }
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 活动主图上传到FTP服务器上
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        //此处有个安全问题，有可能会有憨憨一直传文件上服务器，导致服务器爆炸，虽然限制了一次最多10M戴氏没啥用
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        } else{
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        }
    }


    /**
     * 富文本文件上传到ftp服务器文件
     * @param session
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            resultMap.put("success",false);
            resultMap.put("msg", "请登录管理员");
            return  resultMap;
        }
//        富文本中对于返回值有自己的要求，在这里使用的是simditor所以按照simditor的要求进行返回
//          相关文档：https://simditor.tower.im/docs/doc-usage.html
//        {
//            "success": true/false,
//                "msg": "error message", # optional
//            "file_path": "[real file path]"
//        }
        if(iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path", url);

            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        }else {
            resultMap.put("success",false);
            resultMap.put("msg","无权限操作");
            return resultMap;
        }
    }




}
