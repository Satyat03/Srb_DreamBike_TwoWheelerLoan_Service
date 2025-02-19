package in.srb.dreambiketwowheelerloan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.srb.dreambiketwowheelerloan.exception.AdharCardInvalidException;
import in.srb.dreambiketwowheelerloan.exception.AgeMisMatchException;
import in.srb.dreambiketwowheelerloan.exception.EmailInvalidException;
import in.srb.dreambiketwowheelerloan.exception.MobileNoInvalidException;
import in.srb.dreambiketwowheelerloan.exception.PanCardInvalidException;
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
	public List<CustomerEnquiry> getAllCustomerEnquiryData() {
		List<CustomerEnquiry> all = cr.findAll();
		
		return all;
	}

	@Override
	public CustomerEnquiry updateCustomerEnquiryData(CustomerEnquiry c, int customerId) {
	
			 // Check age validity (for example, age should be between 18 and 100)
	        if (c.getAge() < 18 || c.getAge() > 100) {
	            throw new AgeMisMatchException("Age must be between 18 and 100");
	        }
	        // Validate Email ends with @gmail.com
	        if (!c.getEmail().endsWith("@gmail.com")) {
	            throw new EmailInvalidException("Email must end with @gmail.com");
	        }
	        // validate Mobile No
	        String mob = String.valueOf(c.getMobileno());
	        if(mob.length()!=10 || !mob.matches("\\d{10}")) {
	        	throw new MobileNoInvalidException("Mobile No Not Valid-Must be 10 Digit");
	        }
 
	        // Validate PanCard 
	        String pan=String.valueOf(c.getPancardno());
	        if(!pan.matches("[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
	        	throw new PanCardInvalidException(" Invalid Pan Card");
	        }
	        
	        //validate AdharCard
	        String adhar=String.valueOf(c.getAdharcard());
	        if(adhar.length()!=12) {
	        	throw new AdharCardInvalidException("Inavlid Adhar Card ");
	        }
	        
			
		
		CustomerEnquiry customerEnquiry = cr.save(c);
		return customerEnquiry;
	}

}
