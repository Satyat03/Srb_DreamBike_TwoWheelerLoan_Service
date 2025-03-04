package com.srbdreambike.acc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Data;

@Data
//@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanDisbursement {
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer agreementId;
    private Integer loanNo;
    private String agreementDate;
    private String amountPayType;
    private Double totalAmount;
    private String bankName;
    private Long accountNumber;
    private String ifsccode;
    private String accountType;
    private Double transferAmount;
    private String paymentStatus;
    private String amountPaidDate;

    // Getters and Setters
      

    
    
    
    
    
    
    
    
    
    
    
    
    
}

