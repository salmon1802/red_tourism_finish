package com.redtourism.demo.vo;

import lombok.Data;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Date;

@Data
public class JoinPeopleInfo {
	private Integer useInfoId;
	
	private Integer aId;
	
	private Integer JoinId;
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
	private Boolean isCaptain;
	
	/**
	 * 	0未同意 1已同意 2拒绝
	 */
	private Integer joinStatus;
	
	private Date joinTime;
	
	
}
