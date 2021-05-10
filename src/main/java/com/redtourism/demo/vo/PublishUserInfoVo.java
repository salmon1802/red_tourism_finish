package com.redtourism.demo.vo;

import com.redtourism.demo.pojo.Comment;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class PublishUserInfoVo {
	private String mainPicture;
	private String userPhoto;
	private Date startTime;
	private Date endTime;
	private String userName;
	private String phone;
	private Integer joinPeople;
	private Integer activityPeople;
	private String organization;
	private String activityAddress;
	private BigDecimal activityTime;
	
	
	private String activityTitle;
	private Integer activityStatus;
	
	private String activityContent;
	
	private Integer activityType;
	
	private List<JoinPeople>  joinPeopleInfo;
	
	private Integer activityPoint;  //点赞数
	
	private Integer joinStatus;  //用户加入状态
	
	private List<Comment> comment;
	
	private Boolean isPoint;
	
	
	
	
	
	
	
	
	
	
	
	
	
}
