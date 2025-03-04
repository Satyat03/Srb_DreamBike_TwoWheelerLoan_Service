package in.srb.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanDisbursement {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer agreementId;
    private Integer loanNo;
    private Date agreementDate;
    private String amountPayType;
    private Double totalAmount;
    private String bankName;
    private Long accountNumber;
    private String ifsccode;
    private String accountType;
    private Double transferAmount;
    private String paymentStatus;
    private Date amountPaidDate;

    // Getters and Setters
      

    
    
    
    
    
    
    
    
    
    
    
    
    
}

