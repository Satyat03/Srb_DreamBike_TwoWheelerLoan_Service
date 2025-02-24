package in.srb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class LocalAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer localAddressId;
    private String areaName;
    private String cityName;
    private String district;
    private String state;
    private Long pinCode;
    private Integer houseNumber;
    private String streetName;

    // Getters and Setters
}
