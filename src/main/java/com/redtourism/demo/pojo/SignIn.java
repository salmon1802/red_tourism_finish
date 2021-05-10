package com.redtourism.demo.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "red_tourism_sign")
public class SignIn  {

	/**
	 * 
	 */
	@Id
	private Integer id;
	/**
	 * 
	 */
	private Date startTime;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private Integer aid;
	/**
	 * 
	 */
	private Integer useId;
	/**
	 * 
	 */
	private Date createTime;

}
