package com.srbdreambike.acc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
//@Entity
public class DependentInfo {
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int dependentInfoId;
    private int noOfFamilyMember;
    private int noOfChild;
    private String maritalStatus;
    private String dependentMember;
    private double familyIncome;
    
    

}
