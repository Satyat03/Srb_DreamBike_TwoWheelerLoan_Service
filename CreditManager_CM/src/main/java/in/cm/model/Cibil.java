package in.cm.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cibil {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cibilid;
	private int cibilScore;
	private Date cibilScoreDataTime;
	private String status;
	private String cibilRemark;
	
}
