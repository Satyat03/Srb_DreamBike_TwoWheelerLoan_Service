 package in.srb.dreambiketwowheelerloan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import in.srb.dreambiketwowheelerloan.model.Cibil;
import in.srb.dreambiketwowheelerloan.model.CustomerEnquiry;
import in.srb.dreambiketwowheelerloan.model.EmailSender;
import in.srb.dreambiketwowheelerloan.service.CustomerServiceI;
import in.srb.dreambiketwowheelerloan.utility.EmailService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	EmailService service;
	@Value("${spring.mail.username}")
	String from;
	
	
	@Autowired
	CustomerServiceI csi;
	
	@Autowired
	RestTemplate rs;
	
	
	@PostMapping("/add")
	public ResponseEntity<String> createCustomer(@RequestBody CustomerEnquiry cs) 
	{
		cs.setEnquiryStatus("Pending");	
//		String cibilurl="http://localhost:1001/cibil/"+cs.getCi().getCibilid();
//		Cibil cibilscore= rs.getForObject(cibilurl, Cibil.class);
//		//System.out.println(cibilscore);
	
//		cs.setCi(cibilscore);
//		
//		
		csi.savedata(cs);
		
		return new ResponseEntity<String>("Data Added Successfully !!",HttpStatus.CREATED);
		
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<CustomerEnquiry>> getAllData(){
		List<CustomerEnquiry> list=csi.getAllCustomerEnquiryData();
		return new ResponseEntity<List<CustomerEnquiry>>(list,HttpStatus.OK);
	}
	@PutMapping("/update/{CustomerId}")
	public ResponseEntity<CustomerEnquiry> updatedata(@RequestBody CustomerEnquiry c,@PathVariable int CustomerId){
		CustomerEnquiry ce=csi.updateCustomerEnquiryData(c,CustomerId);
		return new ResponseEntity<CustomerEnquiry>(ce,HttpStatus.ACCEPTED);
	}
	

	

	@DeleteMapping("/deleteById/{CustomerId}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable int CustomerId){
		String string=csi.deleteCustomer(CustomerId);
		return new ResponseEntity<String>(string,HttpStatus.ACCEPTED);
		
	}
	
	@PutMapping("updateCibilStatus/{CustomerId}")
	public ResponseEntity<CustomerEnquiry> updateCibilStatus(@PathVariable int CustomerId){
		CustomerEnquiry customer=csi.getCustomer(CustomerId);
		String cibilurl="http://localhost:1001/"+customer.getCi().getCibilid();
		Cibil cibil= rs.getForObject(cibilurl, Cibil.class);
		customer.setCi(cibil);
		
		CustomerEnquiry customerEnquiry=csi.updateCibilStatus(customer);
		return new ResponseEntity<CustomerEnquiry>(customerEnquiry,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/getby/{CustomerId}")
	public ResponseEntity<CustomerEnquiry> getSingle(@PathVariable ("CustomerId")int CustomerId )
	{
		CustomerEnquiry ce=csi.getSingleRecord(CustomerId);
		
		return new ResponseEntity<CustomerEnquiry>(ce,HttpStatus.OK);
		
		
	}

	@GetMapping("/getStatus/{enquiryStatus}")
	public ResponseEntity<List<CustomerEnquiry>> getEnquirystatus(@PathVariable ("enquiryStatus")String enquiryStatus)
	{
		List<CustomerEnquiry> status = csi.getDataByStatus(enquiryStatus);
		return new ResponseEntity<List<CustomerEnquiry>>(status,HttpStatus.OK);
	
	}

	@PostMapping("/email")
	public String sendMail(@RequestBody EmailSender e)
	{
		try {
			e.setFrom(from);
			service.sendMail(e);
		} catch (Exception e2) {
			return "email not send"+e2;
		}
		return "email send";
		}
	}




