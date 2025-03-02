package in.srb.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.srb.model.Customer;
import in.srb.service.EmailService;
import in.srb.service.LoanServiceI;


//@CrossOrigin("*")
@RequestMapping("/mail")
@RestController
public class EmailController 
{ 
	@Autowired
	EmailService emailservice;
	
	@Autowired
	LoanServiceI cs;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@GetMapping("/sendSantionLetterMail/{customerId}")
	public Customer sendSanctionLetterMail(@PathVariable("customerId") Integer customerId)
	{
		System.out.println("Mail sending started");
		Optional<Customer> customerdetail = cs.findById(customerId);
		Customer customerDetails = customerdetail.get();
		if(customerdetail.isPresent())
		{
			emailservice.sendSantionLetterMail(customerDetails);
			return customerDetails;
		}
		else
		{
			return null;
		}
	}
}
