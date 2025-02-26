package in.srb.controller;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.aspectj.weaver.NewParentTypeMunger; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.srb.model.Customer;
import in.srb.model.CustomerEnquiry;
import in.srb.service.LoanServiceI;

@RestController
@RequestMapping("/loan")
public class loanController {

    @Autowired
    RestTemplate rt;
	
    @Autowired
    LoanServiceI lsi;
    
	@GetMapping("/get")
	public String get()
	{
		return "success";
	}
	
	ObjectMapper mapper=new ObjectMapper();
	 
	@PostMapping("/addData/{CustomerId}")
	public ResponseEntity<Customer> applicationForm(@RequestPart ("json") String jsonData,
			@PathVariable("CustomerId") Integer CustomerId,
			@RequestPart("addressProof") MultipartFile addressProof,
			@RequestPart("panCard") MultipartFile panCard,
			@RequestPart("incomeTax") MultipartFile incomeTax,
			@RequestPart("addharCard") MultipartFile addharCard,
			@RequestPart("photo") MultipartFile photo,
			@RequestPart("signature") MultipartFile signature,
			@RequestPart("bankCheque") MultipartFile bankCheque,
			@RequestPart("salarySlips") MultipartFile salarySlips) throws JsonMappingException, JsonProcessingException{
		
		
		String urlAprroed="http://localhost:1000/customer/approved";
		
//		List<CustomerEnquiry> body = rt.exchange(urlAprroed, HttpMethod.GET,null,new ParameterizedTypeReference<List<CustomerEnquiry>>() {
//		}).getBody();
		
		 CustomerEnquiry[] o = rt.getForObject(urlAprroed, CustomerEnquiry[].class);
		 
	
		Customer c = mapper.readValue(jsonData, Customer.class);
		 List<CustomerEnquiry> body = Arrays.asList(o);
		
		
		for(CustomerEnquiry ce :body) {
			
		//	System.out.println(ce);
			
			if(ce.getCustomerId()==CustomerId) {
				//c.setCustomerId(ce.getCustomerId());
				c.setCustomerName(ce.getFirstName()+ce.getLastName());
				c.setCustomerAge(ce.getAge());
				c.setCustomerEmail(ce.getEmail());
				c.setCustomerMobileNumber(ce.getMobileno());
				c.setCustomerAdditionalMobileNumber(ce.getAltmobileno());
				c.setCustomerAdharCard(ce.getAdharcard());
				c.setCustomerPanCard(ce.getPancardno());
				c.setCibilScore(ce.getCi());
				
				c.setLoanStatus("Submitted");
				//System.out.println(jsonData);
				Customer save =lsi.saveData(c,addressProof,panCard,incomeTax,addharCard,photo,signature,bankCheque,salarySlips);
				
				return new ResponseEntity<Customer>(save,HttpStatus.CREATED);
}
			
		}
		return null;
		
		
		
	}
	
	@GetMapping("/getAllCustomer/{loanStatus}")
	public List<Customer> getAllSubmitted(@PathVariable String loanStatus) {
		List<Customer> customer=lsi.getAllCustomer(loanStatus);
		return customer;
		
	}
	
}
