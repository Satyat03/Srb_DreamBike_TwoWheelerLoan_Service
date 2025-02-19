package in.srb.dreambiketwowheelerloan.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CustomerEnquiry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CustomerId;
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	@Column(name="mobileno",length=10,nullable=false,unique=true)
	private String mobileno;
	private String pancardno;
	private String address;
	@Column(name="altmobileno",length=10,nullable=false,unique=true)
	private String altmobileno;
	@Column(name="adharcard",length=12,nullable=false,unique=true)
	private String adharcard;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	private Cibil ci;
	
	

}
