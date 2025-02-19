package in.srb.dreambiketwowheelerloan.service;

import in.srb.dreambiketwowheelerloan.model.CustomerEnquiry;

public interface CustomerServiceI {

	 public void savedata(CustomerEnquiry cs);

	public String deleteCustomer(int customerId);

	public CustomerEnquiry getCustomer(int customerId);

	public CustomerEnquiry updateCibilStatus(CustomerEnquiry customer);

}
