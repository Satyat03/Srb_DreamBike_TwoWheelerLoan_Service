package in.srb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class GuarantorDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer guarantorId;
    private String guarantorName;
    private String guarantorDateOfBirth;
    private String guarantorRelationshipWithCustomer;
    private Long guarantorMobileNumber;
    private Long guarantorAdharCardNo;
    private String guarantorMortgageDetails;
    private String guarantorJobDetails;
    private String guarantorLocalAddress;
    private String guarantorPermanentAddress;

    
    
    
    
}

