package in.srb.dreambiketwowheelerloan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
		
		return null;
		
	}
	
	
	
}
