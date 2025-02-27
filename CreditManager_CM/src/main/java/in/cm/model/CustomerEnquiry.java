package in.cm.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Data
@Entity
public class CustomerEnquiry {
	
	@Id
	private int CustomerId;
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	//@Column(name="mobileno",length=10,nullable=false,unique=true)
	private String mobileno;
	private String pancardno;
	private String address;
	//@Column(name="altmobileno",length=10,nullable=false,unique=true)
	private String altmobileno;
	//@Column(name="adharcard",length=12,nullable=false,unique=true)
	private String adharcard;
	
	//@Column(name="Enquiry Status")
	private String enquiryStatus;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Cibil ci;
	
	

}
