package in.srb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class DependentInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int dependentInfoId;
	@Min(value = 1, message = "Number of family members must be at least 1")
    private int noOfFamilyMember;
	@Min(value = 0, message = "Number of children cannot be negative")
    private int noOfChild;
	@NotBlank(message = "Marital status cannot be blank")
	@Pattern(regexp = "Single|Married|Divorced|Widowed", message = "Marital status must be Single, Married, Divorced, or Widowed")
    private String maritalStatus;
//	@NotBlank(message = "Dependent member field cannot be blank")
//	@Pattern(regexp = "Yes|No", message = "Dependent member must be Yes or No")
    private String dependentMember;
    @Min(value = 0, message = "Family income cannot be negative")
    private double familyIncome;
}
