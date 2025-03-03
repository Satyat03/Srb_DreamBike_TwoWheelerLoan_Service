package in.cm.model;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class SanctionLetter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int sanctionId;
    private Date sanctionDate;
    private String applicantName;
    private String contactDetails;
    private double loanRequired;
    private Double loanAmtSanctioned;
    private String interestType;
    private float rateOfInterest;
    private int loanTenureInYear;
    private double monthlyEmiAmount;
    private String modeOfPayment;
    private String remarks;
    private String termsCondition;
    private String status;

    // Getters and Setters
    @Lob
	@Column(length = 9000000)
	private byte[] sanctionLetter;
	
//    {
//    	"loanTenureInYear":2
//    }
}

