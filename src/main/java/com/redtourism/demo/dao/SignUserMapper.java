package com.redtourism.demo.dao;

import com.redtourism.demo.pojo.SignUser;
import com.redtourism.demo.util.IMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-01-20 17:12:55
 */

public interface SignUserMapper extends IMapper<SignUser> {

    List<Map<String,Object>> getLastSignUserInfo(Integer aid);


	
}
