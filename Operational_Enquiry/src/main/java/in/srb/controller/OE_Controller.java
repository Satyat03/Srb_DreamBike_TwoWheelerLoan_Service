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
	

	@GetMapping("/getAllSubmitted/{CustomerId}")
	public ResponseEntity<Customer> getAllCustomerSubmitted(@PathVariable("CustomerId") int CustomerId) {
	    String loanUrl = "http://localhost:1003/loan/changestatus/" + CustomerId;

	    // Use ParameterizedTypeReference for List<Customer>
	    ResponseEntity<Customer> response = rt.exchange(
	        loanUrl,
	        HttpMethod.GET, // Assuming the endpoint uses GET
	        null,
	        new ParameterizedTypeReference<Customer>() {}
	    );
	    return response;
	}

	@GetMapping("/getAllSubmitted")
	public ResponseEntity<List<Customer>> getAllCustomerSubmitted() {
	    String loanUrl = "http://localhost:1003/loan/getAllCustomer/Submitted";

	    // Fetch the list of customers with loan status "Submitted"
	    List<Customer> submittedCustomers = rt.getForObject(loanUrl, List.class);

	    // Update loan status to "Verified" for all customers
	    if (submittedCustomers != null && !submittedCustomers.isEmpty()) {
	        for (Customer customer : submittedCustomers) {
	            customer.setLoanStatus("Verified");
	        }
	    }

	    // Return the updated list
	    return new ResponseEntity<>(submittedCustomers, HttpStatus.OK);
	

	}
	
	
	


}
