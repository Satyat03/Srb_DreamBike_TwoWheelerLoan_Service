package com.srbdreambike.acc.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetails {
    private Integer accountId;
    private String accountType;
    private Double accountBalance;
    private String accountHolderName;
    private String accountStatus;
    private Long accountNumber;
    private String bankName;
    private String ifscCode;

    // Getters and Setters
    
    

    
    
    
    
    
    
    
    
    
    
}

