package com.redtourism.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-01-20 17:12:55
 */
@Data
@Table(name = "red_tourism_sign_user")
public class SignUser  {
	
	/**
	 * 
	 */
	@Id
	private Integer id;
	/**
	 * 
	 */
	private Integer signId;
	/**
	 * 
	 */
	private Date signTime;
	/**
	 * 0未签到 1已签到 2 过期
	 */
	private Integer status;
	/**
	 * 
	 */
	private Integer aid;
	/**
	 * 
	 */
	private Integer userId;

}
