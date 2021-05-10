package com.redtourism.demo.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @date 2020-12-1 - 16:23
 * Created by Salmon
 */
public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String PHONE = "phone";

    public static final String USERNAME = "username";

    public interface  ActivityListOrderBy{
        Set<String> PEOPLE_ASC_DESC = Sets.newHashSet("people_desc","people_asc");
    }

    public interface MyActivity{
        int CHECKED = 1;//即活动选中状态
        int UN_CHECKED = 0;//活动未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public interface Role {
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;  //管理员
    }

    public enum ActivityStatusEnum{


        ON_PROCEED(1,"进行");

        private String value;
        private int code;
        ActivityStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }





}
