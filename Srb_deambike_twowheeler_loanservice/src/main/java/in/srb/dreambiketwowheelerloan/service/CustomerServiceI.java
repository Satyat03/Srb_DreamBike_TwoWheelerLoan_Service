package in.srb.dreambiketwowheelerloan.service;

import in.srb.dreambiketwowheelerloan.model.CustomerEnquiry;

public interface CustomerServiceI {

	 public void savedata(CustomerEnquiry cs);

	public CustomerEnquiry getSingleRecord(int customerId);

}
