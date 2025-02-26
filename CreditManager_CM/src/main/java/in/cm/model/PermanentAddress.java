package in.cm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PermanentAddress {

	@Id
    private Integer permanentAddressId;
    private String areaName;
    private String cityName;
    private String district;
    private String state;
    private Long pinCode;
    private Integer houseNumber;
    private String streetName;


    
    
    // Getters and Setters
}

