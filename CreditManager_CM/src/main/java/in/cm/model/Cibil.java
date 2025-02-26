package in.cm.model;

import java.util.Date;


import lombok.Data;


@Data
public class Cibil {

	private int cibilid;
	private int cibilScore;
	private Date cibilScoreDataTime;
	private String status;
	private String cibilRemark;
	
}
