package in.cm.model;


import lombok.Data;

@Data

public class SanctionLetter {
	
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

    // Getters and Setters
}

