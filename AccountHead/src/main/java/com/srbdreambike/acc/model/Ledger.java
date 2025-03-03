package com.srbdreambike.acc.model;


import lombok.Data;

//@Entity
@Data
public class Ledger {

//    @Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer ledgerId;
    private String ledgerCreatedDate;
    private Double totalLoanAmount;
    private Double payableAmountwithInterest;
    private Integer tenure;
    private Double monthlyEMI;
    private Double amountPaidtillDate;
    private Double remainingAmount;
    private String nextEmiDatestart;
    private String nextEmiDateEnd;
    private Integer defaulterCount;
    private String previousEmitStatus;
    private String currentMonthEmiStatus;
    private String loanEndDate;
    private String loanStatus;

    // Getters and Setters
}

