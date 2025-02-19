package in.srb.dreambiketwowheelerloan.service;

import java.util.List;

import in.srb.dreambiketwowheelerloan.model.CustomerEnquiry;

public interface CustomerServiceI {

	 public void savedata(CustomerEnquiry cs);

	public CustomerEnquiry getSingleRecord(int customerId);

	public List<String> getDataByStatus(String status);

}
