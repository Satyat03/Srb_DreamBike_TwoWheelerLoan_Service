 package in.srb.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity

public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer CustomerId;
	private String username;
	private String password;
	private String usertype;
	
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
    @OneToOne(cascade = CascadeType.ALL)
    private AllPersonalDocuments allPersonalDocument;
    @OneToOne(cascade = CascadeType.ALL)// One-to-One
    private DependentInfo familyDependentInfo;
    @OneToOne(cascade = CascadeType.ALL)// One-to-One
    private CustomerAddress customerAddress;
//    @OneToOne(cascade = CascadeType.ALL)// One-to-One
//    private MedicalInfo medicalInfo;
    @OneToOne(cascade = CascadeType.ALL)// One-to-One
    private Cibil cibilScore;          
//    @OneToOne(cascade = CascadeType.ALL)// One-to-One
//    private CurrentLoanDetails currentLoanDetails; 
//    @OneToOne(cascade = CascadeType.ALL)// One-to-One
//    private PreviousLoan previousLoan; 
    @OneToOne(cascade = CascadeType.ALL)// One-to-One
    private AccountDetails accountDetails;  
    @OneToOne(cascade = CascadeType.ALL)// One-to-One
    private GuarantorDetails guarantorDetails;   
    @OneToOne(cascade = CascadeType.ALL)// One-to-One
    private LoanDisbursement loanDisbursement;    
    @OneToMany(cascade = CascadeType.ALL)
     private List<Ledger> ledger;         
    @OneToOne(cascade = CascadeType.ALL)// One-to-Many
    private SanctionLetter sl;    
    @OneToOne(cascade = CascadeType.ALL)// One-to-One
    private CustomerVerification customerVerification; // One-to-One

    // Getters and Setters
}
