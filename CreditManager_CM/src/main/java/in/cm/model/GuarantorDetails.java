package in.cm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class GuarantorDetails {
	@Id
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

