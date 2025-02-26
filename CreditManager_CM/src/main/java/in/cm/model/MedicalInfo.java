package in.cm.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MedicalInfo {
	
	@Id
	private int billId;
    private int patientId;
    private String professionsalPatientName;
    private String billingDate;
    private double loanAmount;
    private String treatment;
}
