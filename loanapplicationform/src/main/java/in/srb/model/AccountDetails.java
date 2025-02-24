package in.srb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class AccountDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer accountId;
	@NotBlank(message = "Account type cannot be blank")
	@Pattern(regexp = "Savings|Current|Fixed", message = "Account type must be Savings, Current, or Fixed")
    private String accountType;
	@NotBlank(message = "Account balance cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Account balance must be greater than 0") 
    private Double accountBalance;
    private String accountHolderName;
    @NotBlank(message = "Account status cannot be blank")
    @Pattern(regexp = "Active|Inactive|Blocked", message = "Account status must be Active, Inactive, or Blocked")
    private String accountStatus;
    @NotNull(message = "Account number cannot be null")
    @Digits(integer = 12, fraction = 0, message = "Account number must be a 12-digit number")
    private Long accountNumber;

    // Getters and Setters
}

