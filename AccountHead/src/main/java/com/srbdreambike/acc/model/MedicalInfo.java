package com.srbdreambike.acc.model;

import lombok.Data;

@Data
//@Entity
public class MedicalInfo {
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int billId;
    private int patientId;
    private String professionsalPatientName;
    private String billingDate;
    private double loanAmount;
    private String treatment;
}
