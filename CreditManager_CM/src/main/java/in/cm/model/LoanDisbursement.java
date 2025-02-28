package in.cm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanDisbursement {
	
	@Id
    private Integer agreementId;
    private Integer loanNo;
    private String agreementDate;
    private String amountPayType;
    private Double totalAmount;
    private String bankName;
    private Long accountNumber;
    private String ifsccode;
    private String accountType;
    private Double transferAmount;
    private String paymentStatus;
    private String amountPaidDate;

    // Getters and Setters
      

    
    
    
    
    
    
    
    
    
    
    
    
    
}

