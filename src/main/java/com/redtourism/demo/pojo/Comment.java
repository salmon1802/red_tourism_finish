package com.redtourism.demo.pojo;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "red_tourism_activity_comment")
public class  Comment  {

	/**
	 * 活动评论表id
	 */
	@Id
	private Integer id;
	/**
	 * 活动id
	 */
	private Integer aid;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 心得
	 */
	private String comment;

	/**
	 * 标记： 唯一表示一条评论
	 */
	private String mark;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 
	 */
	private String userPhoto;
	/**
	 * 
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAid() {
		return aid;
	}
	
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPhoto() {
		return userPhoto;
	}
	
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
