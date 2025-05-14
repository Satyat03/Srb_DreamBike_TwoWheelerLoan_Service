package in.cm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.cm.model.Customer;
import in.cm.service.CMserviceI;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cm")
@CrossOrigin("*")
@Slf4j
public class CmController {

    @Autowired
    CMserviceI cmi;

    @GetMapping("/get")
    public String log() {
        log.info("Health check endpoint called in CmController.");
        return "success!";
    }

    @PutMapping("/addsanction/{CustomerId}/{tenure}")
    public ResponseEntity<Customer> updatesanction(@PathVariable("CustomerId") int CustomerId,
                                                   @PathVariable("tenure") int tenure) {
        log.info("Received request to update sanction for Customer ID: {} with tenure: {} months", CustomerId, tenure);

        try {
            Customer customer = cmi.updateSanction(CustomerId, tenure);
            log.info("Sanction updated successfully for Customer ID: {}", CustomerId);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating sanction for Customer ID: {}", CustomerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
