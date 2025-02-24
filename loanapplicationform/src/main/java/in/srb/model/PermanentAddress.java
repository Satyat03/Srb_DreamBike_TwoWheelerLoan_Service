package in.srb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class PermanentAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer permanentAddressId;
	@NotBlank(message = "Area name cannot be blank")
    @Size(min = 3, max = 50, message = "Area name must be between 3 and 50 characters")
    private String areaName;
	@NotBlank(message = "City name cannot be blank")
    @Size(min = 3, max = 50, message = "City name must be between 3 and 50 characters")
    private String cityName;
	@NotBlank(message = "District name cannot be blank")
    @Size(min = 3, max = 50, message = "District name must be between 3 and 50 characters")
    private String district;
	@NotBlank(message = "state name cannot be blank")
    @Size(min = 3, max = 50, message = "state name must be between 3 and 50 characters")
    private String state;
	@NotNull(message = "Pincode cannot be null")
	@Digits(integer = 6, fraction = 0, message = "Pincode must be a 6-digit number")
    private Long pinCode;
	@NotNull(message = "House number cannot be null")
	@Min(value = 1, message = "House number must be a positive integer")
    private Integer houseNumber;
	@NotBlank(message = "Street name cannot be blank")
    @Size(min = 3, max = 50, message = "Street name must be between 3 and 50 characters")
    private String streetName;

    // Getters and Setters
}

