package in.srb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.srb.model.Customer;
import in.srb.model.CustomerEnquiry;
import in.srb.repo.OERepo;

@Service
public class OEServiceImpl implements OEServiceI{

	@Autowired
	OERepo or;
	
	@Override
	public List<CustomerEnquiry> savedata(CustomerEnquiry cs) {
		List<CustomerEnquiry> save = (List<CustomerEnquiry>) or.save(cs);
		return save;
	}

	@Override
	public void savecustomer(Customer c) {
		
		or.save(c);
		
	}
	
}
