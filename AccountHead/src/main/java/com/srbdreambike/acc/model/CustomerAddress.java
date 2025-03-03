package com.srbdreambike.acc.model;


import lombok.Data;

@Data
//@Entity
public class CustomerAddress {

//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	 private int customerAddressId;
	//@OneToOne(cascade = CascadeType.ALL)
	 private PermanentAddress permanentAddress; 
	//@OneToOne(cascade = CascadeType.ALL)// One-to-one relationship
	 private LocalAddress localAddress;
	

}
