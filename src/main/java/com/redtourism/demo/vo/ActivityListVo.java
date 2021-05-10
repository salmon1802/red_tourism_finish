package com.redtourism.demo.vo;

import java.math.BigDecimal;

/**
 * @date 2021-1-16 - 13:45
 * Created by Salmon
 */
public class ActivityListVo {

    private Integer aid;
    private String mainPicture;
    private Integer activityPeople; //活动最大参加人数
    private String activityTitle;
    private Integer activityStatus;
    private Integer activityType;
    private String activityAddress;
    private Integer joinPeople;  //活动当前参与人数
    private String imageHost;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
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



    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
