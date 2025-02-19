package in.srb.dreambiketwowheelerloan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import in.srb.dreambiketwowheelerloan.model.Cibil;
import in.srb.dreambiketwowheelerloan.model.CustomerEnquiry;
import in.srb.dreambiketwowheelerloan.service.CustomerServiceI;

@RestController
public class CustomerController {

	@Autowired
	CustomerServiceI csi;
	
	@Autowired
	RestTemplate rs;
	
	
	@PostMapping("/getdata")
	public ResponseEntity<String> createCustomer(@RequestBody CustomerEnquiry cs) 
	{
		
		String cibilurl="http://localhost:1001/"+cs.getCi().getCibilid();
		Cibil cibilscore= rs.getForObject(cibilurl, Cibil.class);
		System.out.println(cibilscore);
		
		cs.setCi(cibilscore);
		
		
		csi.savedata(cs);
		
		return new ResponseEntity<String>(HttpStatus.CREATED);
		
	}
	
	@GetMapping("/getby/{CustomerId}")
	public ResponseEntity<CustomerEnquiry> getSingle(@PathVariable ("CustomerId")int CustomerId )
	{
		CustomerEnquiry ce=csi.getSingleRecord(CustomerId);
		
		return new ResponseEntity<CustomerEnquiry>(ce,HttpStatus.OK);
		
		
	}
	
	@GetMapping("/getStatus/{status}")
	public ResponseEntity<List<String>> getEnquirystatus(@PathVariable ("status")String status)
	{
		List<String> ce=csi.getDataByStatus(status);
		
		return new ResponseEntity<List<String>>(ce, HttpStatus.ACCEPTED);
		
		
		
		
		
	}


}
