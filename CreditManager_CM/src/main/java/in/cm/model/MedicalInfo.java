package in.cm.model;


import lombok.Data;

@Data

public class MedicalInfo {
	
	private int billId;
    private int patientId;
    private String professionsalPatientName;
    private String billingDate;
    private double loanAmount;
    private String treatment;
}
