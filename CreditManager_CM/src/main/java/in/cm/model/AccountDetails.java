package in.cm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AccountDetails {
	@Id
    private Integer accountId;
    private String accountType;
    private Double accountBalance;
    private String accountHolderName;
    private String accountStatus;
    private Long accountNumber;

    // Getters and Setters
    
    

    
    
    
    
    
    
    
    
    
    
}

