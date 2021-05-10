package com.redtourism.demo.pojo;



import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-01-18 17:25:02
 */
@Data
@Table(name="red_tourism_user_info")
public class UserInfo  {
	/**
	 *
	 */
	@Id
	private Integer id;
	/**
	 *
	 */
	private Integer userId;
	/**
	 *
	 */
	private String photo;
	/**
	 *
	 */
	private String username;
	/**
	 *
	 */
	private String school;
	/**
	 *
	 */
	private String college;
	/**
	 *
	 */
	private String classes;
	/**
	 *
	 */
	private String gender;
	/**
	 *
	 */
	private Date birthday;
	
	private BigDecimal gongyiTime;
	
	private Integer xinyongTime;
	/**
	 *
	 */
	private String organization;
	/**
	 *
	 */
	private Date createTime;
	/**
	 *
	 */
	private Date updateTime;
	
}
	
