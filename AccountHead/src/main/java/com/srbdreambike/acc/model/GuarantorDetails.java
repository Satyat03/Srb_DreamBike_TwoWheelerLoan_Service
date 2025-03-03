package com.srbdreambike.acc.model;


import lombok.Data;

@Data
//@Entity
public class GuarantorDetails {
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer guarantorId;
    private String guarantorName;
    private String guarantorDateOfBirth;
    private String guarantorRelationshipWithCustomer;
    private Long guarantorMobileNumber;
    private Long guarantorAdharCardNo;
    private String guarantorMortgageDetails;
    private String guarantorJobDetails;
    private String guarantorLocalAddress;
    private String guarantorPermanentAddress;

    
    
    
    
}

