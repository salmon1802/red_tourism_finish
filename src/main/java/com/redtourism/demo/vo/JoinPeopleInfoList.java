package com.redtourism.demo.vo;

import com.redtourism.demo.pojo.Activity;
import lombok.Data;

import java.util.List;

@Data
public class JoinPeopleInfoList {
	private Boolean isCaptain;
	
	private JoinPeopleInfo captainInfo;
	
	private List<JoinPeopleInfo>  joinPeopleInfos;
	
	
	
}
