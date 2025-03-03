package in.srb.model;

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
    private String sanctionDate;
    private String applicantName;
    private Double contactDetails;
    private String productHomeEquity;
    private Double loanAmtSanctioned;
    private String interestType;
    private float rateOfInterest;
    private int loanTenureInYear;
    private Double monthlyEmiAmount;
    private String modeOfPayment;
    private String remarks;
    private String termsCondition;
    private String status;

    @Lob
   	@Column(length = 9000000)
   	private byte[] sanctionLetter;
   	
    // Getters and Setters
}

