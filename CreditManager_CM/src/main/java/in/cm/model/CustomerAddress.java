package in.cm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CustomerAddress {

	@Id
	 private int customerAddressId;

	 private PermanentAddress permanentAddress; 
	
	 private LocalAddress localAddress;
	

}
