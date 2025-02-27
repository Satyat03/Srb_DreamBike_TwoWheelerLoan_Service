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
import in.srb.model.Customer;
import in.srb.model.CustomerEnquiry;
import in.srb.service.OEServiceI;

@RestController
@RequestMapping("/oe")
public class OE_Controller {

	@Autowired
	RestTemplate rt;
	
	@Autowired
	OEServiceI oei;
	
	@GetMapping("/cibilgen")
	public ResponseEntity<?> cibilGenerate() {
	    String statusCheckUrl = "http://localhost:1000/customer/getStatus/Pending";
	    List<CustomerEnquiry> customerEnquiries = new ArrayList<>();

	    try {
	        // Fetch pending enquiries
	        ObjectMapper mapper = new ObjectMapper();
	        List<?> rawList = rt.getForObject(statusCheckUrl, List.class);

	        if (rawList == null || rawList.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("No pending customer enquiries found.");
	        }

	        for (Object item : rawList) {
	            CustomerEnquiry enquiry = mapper.convertValue(item, CustomerEnquiry.class);
	            if (enquiry != null && enquiry.getCi() != null) {
	                customerEnquiries.add(enquiry);
	            } else {
	                // Log and skip invalid entries
	                System.err.println("Skipping invalid CustomerEnquiry: " + item);
	            }
	        }
	    } catch (Exception e) {
	        // Log error and return appropriate response
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Error fetching customer enquiries: " + e.getMessage());
	    }

	    // Process each customer enquiry
	    for (CustomerEnquiry ce : customerEnquiries) {
	        try {
	            String cibilUrl = "http://localhost:1001/cibil/" + ce.getCi().getCibilid();

	            // Fetch CIBIL scores
	            Cibil cibilscore= rt.getForObject(cibilUrl, Cibil.class);
	            ce.setCi(cibilscore);

	            // Update enquiry status
	            ce.setEnquiryStatus(ce.getCi().getStatus());

	            // Save updated entry
	            oei.savedata(ce);
	        } catch (Exception e) {
	            System.err.println("Error processing CustomerEnquiry: " + ce);
	            e.printStackTrace();
	        }
	    }

	    // Return updated list
	    return ResponseEntity.ok(customerEnquiries);
	}
	
	@GetMapping("/getAllSubmitted")
	public ResponseEntity<List<?>>  getAllCustomerSubmitted(){
		String loanUrl="http://localhost:1003/loan/getAllCustomer/Submitted";
		
		List list = rt.getForObject(loanUrl,List.class);
		
		List<Customer> updatedList = new ArrayList<>();
		
		for(Customer c:updatedList)
		{
			c.setLoanStatus("Verified");
		}
		
		return new ResponseEntity<List<?>>(list,HttpStatus.ACCEPTED);
		
		
	}
	


}
