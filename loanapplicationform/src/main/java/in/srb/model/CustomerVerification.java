package in.srb.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class CustomerVerification {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer verificationID;
	@Pattern(
	        regexp = "^\\d{4}-\\d{2}-\\d{2}$", 
	        message = "Date must be in the format YYYY-MM-DD"
	    )
    private Date verificationDate;
    private String status;
    private String remarks;

    // Getters and Setters
}

