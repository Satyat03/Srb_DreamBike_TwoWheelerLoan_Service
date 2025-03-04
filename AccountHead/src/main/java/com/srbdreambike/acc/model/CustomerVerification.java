package com.srbdreambike.acc.model;


import lombok.Data;

@Data
//@Entity
public class CustomerVerification {
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer verificationID;
    private String verificationDate;
    private String status;
    private String remarks;

    // Getters and Setters
}

