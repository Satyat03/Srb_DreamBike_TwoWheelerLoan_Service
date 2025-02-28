package in.cm.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CustomerVerification {
	@Id
    private Integer verificationID;
    private String verificationDate;
    private String status;
    private String remarks;

    // Getters and Setters
}

