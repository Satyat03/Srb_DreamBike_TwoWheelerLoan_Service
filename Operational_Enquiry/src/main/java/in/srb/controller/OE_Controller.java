package in.srb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.srb.model.Cibil;
import in.srb.model.Customer;
import in.srb.model.CustomerEnquiry;
import in.srb.service.OEServiceI;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/oe")
@CrossOrigin("*")
@Slf4j
public class OE_Controller {

    @Autowired
    RestTemplate rt;

    @Autowired
    OEServiceI oei;

    @GetMapping("/cibilgen")
    public ResponseEntity<?> cibilGenerate() {
        String statusCheckUrl = "http://localhost:1000/customer/getStatus/Pending";
        List<CustomerEnquiry> customerEnquiries = new ArrayList<>();

        log.info("Starting CIBIL generation for all pending customer enquiries.");

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<?> rawList = rt.getForObject(statusCheckUrl, List.class);

            if (rawList == null || rawList.isEmpty()) {
                log.warn("No pending customer enquiries found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No pending customer enquiries found.");
            }

            for (Object item : rawList) {
                CustomerEnquiry enquiry = mapper.convertValue(item, CustomerEnquiry.class);
                if (enquiry != null && enquiry.getCi() != null) {
                    customerEnquiries.add(enquiry);
                } else {
                    log.warn("Skipping invalid CustomerEnquiry entry: {}", item);
                }
            }
        } catch (Exception e) {
            log.error("Error fetching customer enquiries: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching customer enquiries: " + e.getMessage());
        }

        for (CustomerEnquiry ce : customerEnquiries) {
            try {
                String cibilUrl = "http://localhost:1001/cibil/" + ce.getCi().getCibilid();
                log.info("Fetching CIBIL score for Customer ID: {}", ce.getCustomerId());

                Cibil cibilscore = rt.getForObject(cibilUrl, Cibil.class);
                ce.setCi(cibilscore);
                ce.setEnquiryStatus(cibilscore.getStatus());

                oei.savedata(ce);
                log.info("CIBIL updated and saved for Customer ID: {}", ce.getCustomerId());
            } catch (Exception e) {
                log.error("Error processing CustomerEnquiry ID {}: {}", ce.getCustomerId(), e.getMessage(), e);
            }
        }

        return ResponseEntity.ok(customerEnquiries);
    }

    @GetMapping("/getAllSubmitted/{CustomerId}")
    public ResponseEntity<Customer> getAllCustomerSubmitted(@PathVariable("CustomerId") int CustomerId) {
        log.info("Fetching submitted data for Customer ID: {}", CustomerId);
        String loanUrl = "http://localhost:1003/loan/changestatus/" + CustomerId;

        ResponseEntity<Customer> response = rt.exchange(
                loanUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Customer>() {}
        );

        log.info("Data fetched for Customer ID: {}", CustomerId);
        return response;
    }

    @GetMapping("/cibilgen/{id}")
    public ResponseEntity<?> cibilGenerateById(@PathVariable int id) {
        log.info("Starting CIBIL generation for Customer ID: {}", id);
        String statusCheckUrl = "http://localhost:1000/customer/getStatus/Pending";

        try {
            ResponseEntity<String> response = rt.exchange(
                    statusCheckUrl,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            List<CustomerEnquiry> customerEnquiries = mapper.readValue(
                    response.getBody(),
                    new TypeReference<List<CustomerEnquiry>>() {}
            );

            if (customerEnquiries == null || customerEnquiries.isEmpty()) {
                log.warn("No pending customer enquiries found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No pending customer enquiries found.");
            }

            CustomerEnquiry targetEnquiry = customerEnquiries.stream()
                    .filter(e -> e.getCustomerId() == id)
                    .findFirst()
                    .orElse(null);

            if (targetEnquiry == null) {
                log.warn("No pending enquiry found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No pending customer enquiry found with ID: " + id);
            }

            if (targetEnquiry.getCi() == null) {
                log.info("No CIBIL data found for Customer ID: {}. Creating default Cibil object.", id);
                targetEnquiry.setCi(new Cibil());
            }

            String cibilUrl = "http://localhost:1001/cibil/" + targetEnquiry.getCi().getCibilid();
            log.info("Fetching CIBIL score from: {}", cibilUrl);
            Cibil cibilScore = rt.getForObject(cibilUrl, Cibil.class);
            targetEnquiry.setCi(cibilScore);

            targetEnquiry.setEnquiryStatus(cibilScore.getStatus());

            CustomerEnquiry updatedEnquiry = oei.savedataNew(targetEnquiry);
            log.info("CIBIL generation completed and saved for Customer ID: {}", id);

            return ResponseEntity.ok(updatedEnquiry);

        } catch (Exception e) {
            log.error("Error processing enquiry ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing customer enquiry with ID " + id + ": " + e.getMessage());
        }
    }
}
