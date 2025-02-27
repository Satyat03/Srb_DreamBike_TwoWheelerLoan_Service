package in.srb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MedicalInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int billId;
    private int patientId;
    private String professionsalPatientName;
    private String billingDate;
    private double loanAmount;
    private String treatment;
}
