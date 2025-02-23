package in.srb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import in.srb.model.Cibil;
import in.srb.model.CustomerEnquiry;
import in.srb.service.OEServiceI;

@RestController
@RequestMapping("/oe")
public class OE_Controller {

	@Autowired
	RestTemplate rt;
	
	@Autowired
	OEServiceI oei;
	
	@GetMapping("/get")
	public String get()
	{
		return "success";
		
	}
	@GetMapping("/cibil")
	public List<CustomerEnquiry> cibilGenerate(CustomerEnquiry cs){
		
		String statusCheck="http://localhost:1000/customer/getStatus/Pending";
		List<CustomerEnquiry> pc = rt.getForObject(statusCheck,List.class);
		for(CustomerEnquiry ce:pc) {
			String cibilurl="http://localhost:1001/cibil/"+cs.getCi().getCibilid();
			
			Cibil cibilscore= rt.getForObject(cibilurl, Cibil.class);
		
			cs.setCi(cibilscore);
			 // Update enquiry status after CIBIL generation
	        cs.setEnquiryStatus("Completed");
	       
	        // Save updated CustomerEnquiry (assuming csi.savedata() handles this)
	        List<CustomerEnquiry> list = oei.savedata(cs);
	       
			
		}
return null;       
		
	}
}
