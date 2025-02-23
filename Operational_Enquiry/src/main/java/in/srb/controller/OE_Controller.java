package in.srb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

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
//	@GetMapping("/cibil")
//	public List<CustomerEnquiry> cibilGenerate(CustomerEnquiry cs){
//		
//		String statusCheck="http://localhost:1000/customer/getStatus/Pending";
//		List<CustomerEnquiry> pc = rt.getForObject(statusCheck,List.class);
//		for(CustomerEnquiry ce:pc) {
//			String cibilurl="http://localhost:1001/cibil/"+cs.getCi().getCibilid();
//			
//			Cibil[] cibilscore= rt.getForObject(cibilurl, Cibil[].class);
//		
//			cs.setCi(cibilscore);
//			 // Update enquiry status after CIBIL generation
//	        cs.setEnquiryStatus("Completed");
//	       
//	        // Save updated CustomerEnquiry (assuming csi.savedata() handles this)
//	        List<CustomerEnquiry> list = oei.savedata(cs);
//	       
//			
//		}
//return null;       
//		
//	}
	
//	@GetMapping("/cibil")
//    public List<CustomerEnquiry> cibilGenerate(CustomerEnquiry cs) {
//        String statusCheck = "http://localhost:1000/customer/getStatus/Pending";
//
//        // Use ObjectMapper to map LinkedHashMap to CustomerEnquiry
//        List<CustomerEnquiry> customerEnquiries = new ArrayList<>();
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            List<?> rawList = rt.getForObject(statusCheck, List.class);
//
//            for (Object item : rawList) {
//                CustomerEnquiry enquiry = mapper.convertValue(item, CustomerEnquiry.class);
//                customerEnquiries.add(enquiry);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Handle mapping exceptions
//            return null;
//        }
//
//        for (CustomerEnquiry ce : customerEnquiries) {
//            String cibilUrl = "http://localhost:1001/cibil/" + ce.getCi().getCibilid();
//
//            // Fetch CIBIL score as an array
//            Cibil[] cibilScores = rt.getForObject(cibilUrl, Cibil[].class);
//
//            if (cibilScores != null && cibilScores.length > 0) {
//                ce.setCi(cibilScores[0]); // Assuming you need only the first CIBIL object
//            }
//
//            // Update enquiry status after CIBIL generation
//            ce.setEnquiryStatus("Completed");
//
//            // Save updated CustomerEnquiry
//            oei.savedata(ce);
//        }
//
//        return customerEnquiries; // Return the updated list
//    }
	
	
	
	@GetMapping("/cibil")
	public List<CustomerEnquiry> cibilGenerate(CustomerEnquiry cs) {
	    String statusCheck = "http://localhost:1000/customer/getStatus/Pending";

	    // Use ObjectMapper to map LinkedHashMap to CustomerEnquiry
	    List<CustomerEnquiry> customerEnquiries = new ArrayList<>();
	    try {
	        ObjectMapper mapper = new ObjectMapper();
	        List<?> rawList = rt.getForObject(statusCheck, List.class);

	        for (Object item : rawList) {
	            CustomerEnquiry enquiry = mapper.convertValue(item, CustomerEnquiry.class);
	            customerEnquiries.add(enquiry);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle mapping exceptions
	        return null;
	    }

	    for (CustomerEnquiry ce : customerEnquiries) {
	        if (ce.getCi() == null) {
	            System.err.println("Skipping CustomerEnquiry with null 'ci' field.");
	            continue; // Skip entries without a 'ci' field
	        }

	        String cibilUrl = "http://localhost:1001/cibil/" + ce.getCi().getCibilid();

	        // Fetch CIBIL score as an array
	        Cibil[] cibilScores = rt.getForObject(cibilUrl, Cibil[].class);

	        if (cibilScores != null && cibilScores.length > 0) {
	            ce.setCi(cibilScores[0]); // Assuming you need only the first CIBIL object
	        } else {
	            System.err.println("No CIBIL scores found for Cibil ID: " + ce.getCi().getCibilid());
	            continue;
	        }

	        // Update enquiry status after CIBIL generation
	        ce.setEnquiryStatus("Completed");

	        // Save updated CustomerEnquiry
	        oei.savedata(ce);
	    }

	    return customerEnquiries; // Return the updated list
	}

}
