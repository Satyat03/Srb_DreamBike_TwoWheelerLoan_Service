 package in.cm.model;


import lombok.Data;

@Data
public class Customer {

    private Integer CustomerId;
	
    private String customerName;
    private String customerMobileNumber;
    private Integer customerAge;
    private String customerAdditionalMobileNumber;
    private String customerEmail;
    private String customerAdharCard;
    private String customerPanCard;
    
    
    private String customerDateOfBirth;
    private String customerGender;
    private Double customerAmountPaidForBike;
    private Double customerTotalLoanRequired;
    private String loanStatus; // Values: Submit, verified, sanctioned, disbursed

 
    
    // Relationships
   
    private AllPersonalDocuments allPersonalDocument;
   
    private DependentInfo familyDependentInfo;
   
    private CustomerAddress customerAddress;

    private Cibil cibilScore;          

    private AccountDetails accountDetails;  
 
    private GuarantorDetails guarantorDetails;   
  
    private LoanDisbursement loanDisbursement;    
       
    private SanctionLetter sanctionLetter;    
   
    private CustomerVerification customerVerification; 

    // Getters and Setters
}
