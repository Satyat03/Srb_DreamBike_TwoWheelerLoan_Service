package com.srbdreambike.acc.model;

import java.util.Date;


import lombok.Data;

@Data
public class Cibil {

//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cibilid;
	private int cibilScore;
	private Date cibilScoreDataTime;
	private String status;
	private String cibilRemark;
	
}
