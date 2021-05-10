package com.redtourism.demo.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @date 2021-1-16 - 17:12
 * Created by Salmon
 */
public class ActivityJoinVo {

    List<ActivityJoinItemVo> activityJoinItemVoList;
    private Boolean allChecked;//全部勾选
    private String imageHost;

    public List<ActivityJoinItemVo> getActivityJoinItemVoList() {
        return activityJoinItemVoList;
    }

    public void setActivityJoinItemVoList(List<ActivityJoinItemVo> activityJoinItemVoList) {
        this.activityJoinItemVoList = activityJoinItemVoList;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
