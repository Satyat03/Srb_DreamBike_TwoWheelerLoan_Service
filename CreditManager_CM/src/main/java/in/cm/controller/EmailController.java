package in.cm.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cm.model.Customer;
import in.cm.service.CMserviceI;
import in.cm.service.EmailService;

//@CrossOrigin("*")
@RequestMapping("/mail")
@RestController
public class EmailController 
{ 
	@Autowired
	EmailService emailservice;
	
	@Autowired
   CMserviceI cs;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
//	@GetMapping("/sendSantionLetterMail/{customerId}")
//	public Customer sendSanctionLetterMail(@PathVariable("customerId") Integer customerId)
//	{
//		System.out.println("Mail sending started");
//		Optional<Customer> customerdetail = cs.findById(customerId);
//		Customer customerDetails = customerdetail.get();
//		if(customerdetail.isPresent())
//		{
//			customerdetail.get().getSl().setStatus("Offered");
//			
//			cs.saveData(customerDetails);
//
//			System.out.println(customerdetail.get().getSl().getStatus());
//			emailservice.sendSantionLetterMail(customerDetails);
//			return customerDetails;
//		}
//		else
//		{
//			return null;
//		}
//	}
	
	
	@GetMapping("/sendSantionLetterMail/{customerId}")
	public ResponseEntity<?> sendSanctionLetterMail(@PathVariable("customerId") Integer customerId) {
	    Optional<Customer> customerdetail = cs.findById(customerId);
	    if (customerdetail.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("Customer not found for id: " + customerId);
	    }

	    Customer customerDetails = customerdetail.get();
	    customerDetails.getSl().setStatus("Offered");
	    cs.saveData(customerDetails);
	    emailservice.sendSantionLetterMail(customerDetails);

	    return ResponseEntity.ok(customerDetails);
	}

}
