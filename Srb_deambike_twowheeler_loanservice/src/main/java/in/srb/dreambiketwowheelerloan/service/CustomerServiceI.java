package in.srb.dreambiketwowheelerloan.service;

import java.util.List;

import in.srb.dreambiketwowheelerloan.model.CustomerEnquiry;

public interface CustomerServiceI {

	 public void savedata(CustomerEnquiry cs);


	 public List<CustomerEnquiry>getAllCustomerEnquiryData();

	public CustomerEnquiry updateCustomerEnquiryData(CustomerEnquiry c, int customerId);

	public CustomerEnquiry getSingleRecord(int customerId);


}
