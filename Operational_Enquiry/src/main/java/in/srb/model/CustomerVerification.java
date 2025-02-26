package in.srb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CustomerVerification {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer verificationID;
    private String verificationDate;
    private String status;
    private String remarks;

    // Getters and Setters
}

