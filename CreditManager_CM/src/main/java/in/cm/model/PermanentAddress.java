package in.cm.model;

import lombok.Data;

@Data

public class PermanentAddress {

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

