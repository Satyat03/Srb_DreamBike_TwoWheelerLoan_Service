package in.cm.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.cm.model.Customer;
import in.cm.service.CMserviceI;
import in.cm.service.EmailService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RequestMapping("/mail")
@RestController
@Slf4j
public class EmailController {

    @Autowired
    EmailService emailservice;

    @Autowired
    CMserviceI cs;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @GetMapping("/sendSantionLetterMail/{customerId}")
    public ResponseEntity<?> sendSanctionLetterMail(@PathVariable("customerId") Integer customerId) {
        log.info("Received request to send sanction letter mail for Customer ID: {}", customerId);

        try {
            Optional<Customer> customerdetail = cs.findById(customerId);
            if (customerdetail.isEmpty()) {
                log.warn("Customer not found for ID: {}", customerId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Customer not found for id: " + customerId);
            }

            Customer customerDetails = customerdetail.get();
            customerDetails.getSl().setStatus("Offered");
            cs.saveData(customerDetails);
            emailservice.sendSantionLetterMail(customerDetails);

            log.info("Sanction letter email sent successfully to Customer ID: {}", customerId);
            return ResponseEntity.ok(customerDetails);
        } catch (Exception e) {
            log.error("Error sending sanction letter email for Customer ID: {}", customerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }
}
