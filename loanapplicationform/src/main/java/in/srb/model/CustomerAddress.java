package in.srb.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class CustomerAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	 private int customerAddressId;
	@OneToOne(cascade = CascadeType.ALL)
	 private PermanentAddress permanentAddress; 
	@OneToOne(cascade = CascadeType.ALL)// One-to-one relationship
	 private LocalAddress localAddress;
}
