package in.srb.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.srb.model.Customer;
import in.srb.model.CustomerEnquiry;
import in.srb.service.LoanServiceI;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/loan")
@CrossOrigin("*")
@Slf4j
public class loanController {

    @Autowired
    RestTemplate rt;

    @Autowired
    LoanServiceI lsi;

    ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/get")
    public String get() {
        log.info("Health check endpoint called.");
        return "success";
    }

    @PostMapping("/addData/{CustomerId}")
    public ResponseEntity<Customer> applicationForm(
            @RequestPart("json") String jsonData,
            @PathVariable("CustomerId") Integer CustomerId,
            @RequestPart("addressProof") MultipartFile addressProof,
            @RequestPart("panCard") MultipartFile panCard,
            @RequestPart("incomeTax") MultipartFile incomeTax,
            @RequestPart("addharCard") MultipartFile addharCard,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("signature") MultipartFile signature,
            @RequestPart("bankCheque") MultipartFile bankCheque,
            @RequestPart("salarySlips") MultipartFile salarySlips) throws Exception {

        log.info("Received loan application for Customer ID: {}", CustomerId);

        String urlApproved = "http://localhost:1000/customer/approved";

        CustomerEnquiry[] enquiries = rt.getForObject(urlApproved, CustomerEnquiry[].class);
        List<CustomerEnquiry> enquiryList = Arrays.asList(enquiries);

        Customer customer = mapper.readValue(jsonData, Customer.class);
        log.info("Parsed customer JSON data for Customer ID: {}", CustomerId);

        for (CustomerEnquiry ce : enquiryList) {
            if (ce.getCustomerId() == CustomerId) {
                log.info("Matching enquiry found for Customer ID: {}", CustomerId);

                customer.setCustomerName(ce.getFirstName() + ce.getLastName());
                customer.setCustomerAge(ce.getAge());
                customer.setCustomerEmail(ce.getEmail());
                customer.setCustomerMobileNumber(ce.getMobileno());
                customer.setCustomerAdditionalMobileNumber(ce.getAltmobileno());
                customer.setCustomerAdharCard(ce.getAdharcard());
                customer.setCustomerPanCard(ce.getPancardno());
                customer.setCibilScore(ce.getCi());
                customer.setLoanStatus("Submitted");
                customer.setUsertype("Customer");

                Customer saved = lsi.saveData(customer, addressProof, panCard, incomeTax, addharCard, photo, signature, bankCheque, salarySlips);
                if (saved != null) {
                    rt.put("http://localhost:1000/customer/update/" + ce.getCustomerId(), ce);
                    log.info("Customer application saved and enquiry updated for ID: {}", ce.getCustomerId());
                }

                return new ResponseEntity<>(saved, HttpStatus.OK);
            }
        }

        log.warn("No matching enquiry found for Customer ID: {}", CustomerId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/getAllCustomer/{loanStatus}")
    public List<Customer> getAllSubmitted(@PathVariable String loanStatus) {
        log.info("Fetching all customers with loan status: {}", loanStatus);
        return lsi.getAllCustomer(loanStatus);
    }

    @GetMapping("/customerLogin/{username}/{password}")
    public ResponseEntity<Customer> customerLogin(@PathVariable("username") String username, @PathVariable("password") String password) {
        log.info("Customer login attempt for username: {}", username);
        Customer customer = lsi.getCustomer(username, password);
        return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
    }

    @PutMapping("/customerResponse/{id}/{status}")
    public ResponseEntity<Customer> customerAcceptOrReject(@PathVariable("status") String status, @PathVariable("id") int id) {
        log.info("Customer ID {} responded with status: {}", id, status);
        Customer customer = lsi.customerAcceptance(status, id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/loanDisburse/{CustomerId}")
    public ResponseEntity<String> loanDisbursement(@RequestBody Customer customer, @PathVariable("CustomerId") int CustomerId) {
        log.info("Loan disbursement initiated for Customer ID: {}", CustomerId);
        String disbursement = lsi.updateLoanDisbursement(customer, CustomerId);
        return new ResponseEntity<>(disbursement, HttpStatus.CREATED);
    }

    @GetMapping("/changestatus/{CustomerId}")
    public ResponseEntity<Customer> changestatus(@PathVariable("CustomerId") int CustomerId) {
        log.info("Changing status for Customer ID: {}", CustomerId);
        Customer updatedCustomer = lsi.changestatus(CustomerId);
        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping("/legergen/{CustomerId}/{payment}")
    public Customer loanDisbursement(@PathVariable("CustomerId") int CustomerId, @PathVariable("payment") Double payment) throws Exception {
        log.info("Generating ledger for Customer ID: {} with payment: {}", CustomerId, payment);
        return lsi.createleager(CustomerId, payment);
    }

    @GetMapping("/getCustomer/{id}")
    public Customer getCustomerById(@PathVariable("id") int id) {
        log.info("Fetching customer by ID: {}", id);
        return lsi.getCustomerById(id);
    }
}
