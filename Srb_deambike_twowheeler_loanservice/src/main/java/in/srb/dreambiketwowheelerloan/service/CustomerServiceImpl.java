package in.srb.dreambiketwowheelerloan.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.srb.dreambiketwowheelerloan.model.CustomerEnquiry;
import in.srb.dreambiketwowheelerloan.repo.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerServiceI {

	@Autowired
	CustomerRepo cr;
	
	@Override
	public void savedata(CustomerEnquiry cs) {
		 cr.save(cs);
		
	}

	@Override
	public String deleteCustomer(int customerId) {
		cr.deleteById(customerId);
		return "Delete Customer successfully with Id: "+customerId;
	}

	@Override
	public CustomerEnquiry getCustomer(int customerId) {
		Optional<CustomerEnquiry> byId = cr.findById(customerId);
		if(byId.isPresent()) {
		 return byId.get();
		}
		return null;
	}

	@Override
	public CustomerEnquiry updateCibilStatus(CustomerEnquiry customer) {
		return cr.save(customer);
	}

}
