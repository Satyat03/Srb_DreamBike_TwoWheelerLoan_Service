package in.srb.dreambiketwowheelerloan.service;

import java.util.List;

import in.srb.dreambiketwowheelerloan.model.CustomerEnquiry;

public interface CustomerServiceI {

	 public void savedata(CustomerEnquiry cs);


	public String deleteCustomer(int customerId);

	public CustomerEnquiry getCustomer(int customerId);

	public CustomerEnquiry updateCibilStatus(CustomerEnquiry customer);


	 public List<CustomerEnquiry>getAllCustomerEnquiryData();

	public CustomerEnquiry updateCustomerEnquiryData(CustomerEnquiry c, int customerId);

	public CustomerEnquiry getSingleRecord(int customerId);



}
