package in.srb.dreambiketwowheelerloan.service;

import java.util.List;
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
	public CustomerEnquiry getSingleRecord(int customerId) 
	{
		Optional<CustomerEnquiry> op=cr.findById(customerId);
		
				if(op.isPresent())
				{
					CustomerEnquiry cusEn=op.get();
					
					return cusEn;
				}
		
		
		
		return null;
	}

	@Override
	public List<String> getDataByStatus(String status)
	{
		List<String> op=cr.findAllByStatus(status);
		
			
		
		return op;
	}

}
