package in.srb.dreambiketwowheelerloan.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin("*")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    EmailService service;

    @Value("${spring.mail.username}")
    String from;

    @Autowired
    CustomerServiceI csi;

    @Autowired
    RestTemplate rs;

    @PostMapping("/add")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerEnquiry cs) {
        LOGGER.info("Entering createCustomer with data: {}", cs);
        cs.setEnquiryStatus("Pending");
        csi.savedata(cs);
        LOGGER.info("Customer enquiry saved successfully.");
        return new ResponseEntity<>("Data Added Successfully !!", HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerEnquiry>> getAllData() {
        LOGGER.info("Fetching all customer enquiries");
        List<CustomerEnquiry> list = csi.getAllCustomerEnquiryData();
        LOGGER.info("Found {} customer enquiries", list.size());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/update/{CustomerId}")
    public ResponseEntity<CustomerEnquiry> updatedata(@RequestBody CustomerEnquiry c, @PathVariable int CustomerId) {
        LOGGER.info("Updating customer enquiry with ID: {}", CustomerId);
        CustomerEnquiry ce = csi.updateCustomerEnquiryData(c, CustomerId);
        LOGGER.info("Customer enquiry updated: {}", ce);
        return new ResponseEntity<>(ce, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteById/{CustomerId}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable int CustomerId) {
        LOGGER.info("Deleting customer enquiry with ID: {}", CustomerId);
        String result = csi.deleteCustomer(CustomerId);
        LOGGER.info("Delete operation result: {}", result);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @PutMapping("updateCibilStatus/{CustomerId}")
    public ResponseEntity<CustomerEnquiry> updateCibilStatus(@PathVariable("CustomerId") int CustomerId) {
        LOGGER.info("Updating CIBIL status for customer ID: {}", CustomerId);
        CustomerEnquiry customer = csi.getCustomer(CustomerId);
        String cibilUrl = "http://localhost:1001/cibil/" + customer.getCi().getCibilid();
        LOGGER.info("Calling external CIBIL service: {}", cibilUrl);
        Cibil cibil = rs.getForObject(cibilUrl, Cibil.class);
        customer.setCi(cibil);
        CustomerEnquiry updated = csi.updateCibilStatus(customer);
        LOGGER.info("CIBIL status updated for customer: {}", updated);
        return new ResponseEntity<>(updated, HttpStatus.CREATED);
    }

    @GetMapping("/getby/{CustomerId}")
    public ResponseEntity<CustomerEnquiry> getSingle(@PathVariable("CustomerId") int CustomerId) {
        LOGGER.info("Fetching single customer enquiry by ID: {}", CustomerId);
        CustomerEnquiry ce = csi.getSingleRecord(CustomerId);
        LOGGER.info("Customer enquiry retrieved: {}", ce);
        return new ResponseEntity<>(ce, HttpStatus.OK);
    }

    @GetMapping("/getStatus/{enquiryStatus}")
    public ResponseEntity<List<CustomerEnquiry>> getEnquirystatus(@PathVariable("enquiryStatus") String enquiryStatus) {
        LOGGER.info("Fetching customer enquiries with status: {}", enquiryStatus);
        List<CustomerEnquiry> statusList = csi.getDataByStatus(enquiryStatus);
        LOGGER.info("Found {} customer enquiries with status '{}'", statusList.size(), enquiryStatus);
        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }

    @PostMapping("/email")
    public String sendMail(@RequestBody EmailSender e) {
        LOGGER.info("Sending email to: {}", e.getTo());
        try {
            e.setFrom(from);
            service.sendMail(e);
        } catch (Exception ex) {
            LOGGER.error("Failed to send email", ex);
            return "email not send: " + ex.getMessage();
        }
        LOGGER.info("Email sent successfully to: {}", e.getTo());
        return "email send";
    }

    @GetMapping("/approved")
    public ResponseEntity<List<CustomerEnquiry>> approvedCustomer() {
        LOGGER.info("Fetching approved customer enquiries");
        List<CustomerEnquiry> approvedStatus = csi.findApprovedStatus();
        LOGGER.info("Found {} approved enquiries", approvedStatus.size());
        return new ResponseEntity<>(approvedStatus, HttpStatus.OK);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<CustomerEnquiry>> rejectedCustomer() {
        LOGGER.info("Fetching rejected customer enquiries");
        List<CustomerEnquiry> rejectedStatus = csi.findRejectedStatus();
        LOGGER.info("Found {} rejected enquiries", rejectedStatus.size());
        return new ResponseEntity<>(rejectedStatus, HttpStatus.FOUND);
    }
}
