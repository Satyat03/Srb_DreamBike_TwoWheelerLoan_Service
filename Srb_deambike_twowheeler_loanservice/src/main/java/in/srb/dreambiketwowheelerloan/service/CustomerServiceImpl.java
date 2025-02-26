package in.srb.dreambiketwowheelerloan.service;


import java.util.List;


import java.util.Optional;



import java.util.List;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.srb.dreambiketwowheelerloan.exception.AdharCardInvalidException;
import in.srb.dreambiketwowheelerloan.exception.AgeMisMatchException;
import in.srb.dreambiketwowheelerloan.exception.CaseMisMatchException;
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
	public void savedata(CustomerEnquiry c) {
		
		
		// Validate First Name is in uppercase
        if (!c.getFirstName().equals(c.getFirstName().toUpperCase())) {
            throw new CaseMisMatchException("First name must be in uppercase");
        }
        
       // Validate Last Name is in uppercase
        if (!c.getLastName().equals(c.getLastName().toUpperCase())) {
            throw new CaseMisMatchException("Last name must be in uppercase");
        }
		
		
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
        // validate Alternate No
        String altern = String.valueOf(c.getAltmobileno());
        if(altern.length()!=10 || !altern.matches("\\d{10}")) {
        	throw new MobileNoInvalidException("Alternate Mobile No Not Valid-Must be 10 Digit");
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
        
		
		 cr.save(c);
		
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
	        
		
		CustomerEnquiry ce = cr.save(c);
		
		return ce;
	}

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

	@Override
	public List<CustomerEnquiry> getDataByStatus(String enquiryStatus)
	{
		List<CustomerEnquiry> pendingEnquiries = cr.findPendingEnquiries();
		return pendingEnquiries;
	}

	@Override
	public List<CustomerEnquiry> findApprovedStatus() {
		List<CustomerEnquiry> approved = cr.findApproved();
		return approved;
	}

	@Override
	public List<CustomerEnquiry> findRejectedStatus() {
		List<CustomerEnquiry> rejected = cr.findRejected();
		return rejected;
	}

}
