package in.srb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AccountDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer accountId;
    private String accountType;
    private Double accountBalance;
    private String accountHolderName;
    private String accountStatus;
    private Long accountNumber;
    private String bankName;
    private String ifscCode;

    // Getters and Setters
    
    

    
    
    
    
    
    
    
    
    
    
}

