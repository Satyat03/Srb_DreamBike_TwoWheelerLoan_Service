package in.srb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class DependentInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int dependentInfoId;
    private int noOfFamilyMember;
    private int noOfChild;
    private String maritalStatus;
    private String dependentMember;
    private double familyIncome;
    
    

}
