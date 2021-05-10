package com.redtourism.demo.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "red_tourism_activity")
public class Activity {
    //@Id
    private Integer id;
    
    private Integer userId;
    
    private String mainPicture;
    
    private Integer activityPeople;
    
    private String activityTitle;
    
    private String activityContent;
    
    private Integer activityStatus;
    
    private Integer activityType;
    
    private BigDecimal activityTime;
    
    private Integer activityPoint;
    
    
    private String activityAddress;
    
    private Integer joinpeople;
    
    private Date createTime;
    
    private Date updateTime;
    
    
    public Activity(Integer id, Integer userId, String mainPicture, Integer activityPeople, String activityTitle, String activityContent, Integer activityStatus, Integer activityType, BigDecimal activityTime, Integer activityPoint, String activityAddress, Integer joinpeople, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.mainPicture = mainPicture;
        this.activityPeople = activityPeople;
        this.activityTitle = activityTitle;
        this.activityContent = activityContent;
        this.activityStatus = activityStatus;
        this.activityType = activityType;
        this.activityTime = activityTime;
        this.activityPoint = activityPoint;
        this.activityAddress = activityAddress;
        this.joinpeople = joinpeople;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getMainPicture() {
        return mainPicture;
    }
    
    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
    }
    
    public Integer getActivityPeople() {
        return activityPeople;
    }
    
    public void setActivityPeople(Integer activityPeople) {
        this.activityPeople = activityPeople;
    }
    
    public String getActivityTitle() {
        return activityTitle;
    }
    
    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }
    
    public String getActivityContent() {
        return activityContent;
    }
    
    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }
    
    public Integer getActivityStatus() {
        return activityStatus;
    }
    
    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }
    
    public Integer getActivityType() {
        return activityType;
    }
    
    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }
    
    public Integer getActivityPoint() {
        return activityPoint;
    }
    
    public void setActivityPoint(Integer activityPoint) {
        this.activityPoint = activityPoint;
    }
    
    public BigDecimal getActivityTime() {
        return activityTime;
    }
    
    public void setActivityTime(BigDecimal activityTime) {
        this.activityTime = activityTime;
    }
    
    public String getActivityAddress() {
        return activityAddress;
    }
    
    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }
    
    public Integer getJoinpeople() {
        return joinpeople;
    }
    
    public void setJoinpeople(Integer joinpeople) {
        this.joinpeople = joinpeople;
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