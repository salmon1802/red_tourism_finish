package com.redtourism.demo.vo;

import java.math.BigDecimal;

/**
 * @date 2021-1-16 - 17:14
 * Created by Salmon
 */
public class ActivityJoinItemVo {

    private Integer id; //参加人员表id
    private Integer aid;
    private Integer userId;  //创建者id
    private String mainPicture;
    private Integer activityPeople; //活动最大参加人数
    private String activityTitle;
    private String activityContent;
    private Integer activityStatus;
    private Integer activityType;
    private String activityAddress;
    private Integer joinPeople;  //活动当前参与人数
    private Integer quantity; //当前活动的剩余报名数量



    private Integer activityChecked;//此商品是否勾选

    private String limitQuantity;//限制数量的一个返回结果

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

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

    public String getActivityAddress() {
        return activityAddress;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }

    public Integer getJoinPeople() {
        return joinPeople;
    }

    public void setJoinPeople(Integer joinPeople) {
        this.joinPeople = joinPeople;
    }

    public Integer getActivityChecked() {
        return activityChecked;
    }

    public void setActivityChecked(Integer activityChecked) {
        this.activityChecked = activityChecked;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }
}
