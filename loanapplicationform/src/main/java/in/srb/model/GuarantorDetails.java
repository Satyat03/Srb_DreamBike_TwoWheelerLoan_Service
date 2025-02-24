package in.srb.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class GuarantorDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer guarantorId;
	@NotBlank
    private String guarantorName;
    @Pattern(
	        regexp = "^\\d{4}-\\d{2}-\\d{2}$", 
	        message = "Date must be in the format YYYY-MM-DD"
	    )
    private LocalDate guarantorDateOfBirth;
    private String guarantorRelationshipWithCustomer;
    @NotNull(message = "Mobile number cannot be null")
    @Digits(integer = 10, fraction = 0, message = "Mobile number must be exactly 10 digits")
    private Long guarantorMobileNumber;
    @NotNull(message = "aadhar number cannot be null")
    @Digits(integer = 12, fraction = 0, message = "aadhar number must be exactly 10 digits")
    private Long guarantorAdharCardNo;
    private String guarantorMortgageDetails;
    private String guarantorJobDetails;
    private String guarantorLocalAddress;
    private String guarantorPermanentAddress;

    // Getters and Setters
}

