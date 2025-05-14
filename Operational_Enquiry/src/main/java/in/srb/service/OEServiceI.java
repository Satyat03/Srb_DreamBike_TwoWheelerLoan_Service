package in.srb.service;

import java.util.List;

import in.srb.model.Customer;
import in.srb.model.CustomerEnquiry;

public interface OEServiceI {

	public List<CustomerEnquiry> savedata(CustomerEnquiry cs);
	 
	public void savecustomer(Customer c);

	public CustomerEnquiry savedataNew(CustomerEnquiry targetEnquiry);
	
}
