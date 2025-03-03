package com.srbdreambike.acc.model;


import lombok.Data;

@Data
//@Entity
public class LocalAddress {
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
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
