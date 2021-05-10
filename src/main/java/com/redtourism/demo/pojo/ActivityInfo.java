package com.redtourism.demo.pojo;

import java.util.Date;

public class ActivityInfo {
    private Integer id;

    private Integer aid;

    private Integer userId;

    private String content;

    private Integer point;

    private Date createTime;

    private Date updateTime;

    public ActivityInfo(Integer id, Integer aid, Integer userId, String content, Integer point, Date createTime, Date updateTime) {
        this.id = id;
        this.aid = aid;
        this.userId = userId;
        this.content = content;
        this.point = point;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ActivityInfo() {
        super();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getpoint() {
        return point;
    }

    public void setpoint(Integer point) {
        this.point = point;
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