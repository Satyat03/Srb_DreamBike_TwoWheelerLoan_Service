package in.srb.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
@Entity
public class Customer {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer customerId;
    private String customerName;
    @NotBlank(message = "Date of birth cannot be blank")
    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}$", 
        message = "Date of birth must be in the format YYYY-MM-DD"
    )
    private String customerDateOfBirth;
    private Integer customerAge;
    @NotBlank(message = "Gender cannot be blank")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String customerGender;
    private String customerEmail;
    private Double customerMobileNumber;
    private Double customerAdditionalMobileNumber;
    @NotNull(message = "Amount paid cannot be null")
    @DecimalMin(value = "0.1", inclusive = true, message = "Amount paid must be greater than 0")
    private Double customerAmountPaidForBike;
    @NotNull(message = "Amount paid cannot be null")
    @DecimalMin(value = "0.1", inclusive = true, message = "Amount paid must be greater than 0")
    private Double customerTotalLoanRequired;
    private String loanStatus; // Values: Submit, verified, sanctioned, disbursed

    // Relationships
    @OneToOne(cascade = CascadeType.ALL)
    private AllPersonalDocuments allPersonalDocument;
    @OneToOne(cascade = CascadeType.ALL)// One-to-One
    private DependentInfo familyDependentInfo;
    @OneToOne(cascade = CascadeType.ALL)// One-to-One
    private CustomerAddress customerAddress;
    @OneToOne(cascade = CascadeType.ALL)// One-to-One
    private MedicalInfo medicalInfo;
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
//    @OneToMany(cascade = CascadeType.ALL)
//    private Ledger ledger;         
    @OneToOne(cascade = CascadeType.ALL)// One-to-Many
    private SanctionLetter sanctionLetter;    
    @OneToOne(cascade = CascadeType.ALL)// One-to-One
    private CustomerVerification customerVerification; // One-to-One

    // Getters and Setters
}
