package in.cm.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class CustomerAddress {

	@Id
	 private int customerAddressId;

    @OneToOne(cascade = CascadeType.ALL)// One-to-Many
	 private PermanentAddress permanentAddress; 
    @OneToOne(cascade = CascadeType.ALL)// One-to-Many
	 private LocalAddress localAddress;
	

}
