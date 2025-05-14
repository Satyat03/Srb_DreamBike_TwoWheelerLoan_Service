package com.srbdreambike.acc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.srbdreambike.acc.model.Customer;
import com.srbdreambike.acc.model.LoanDisbursement;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/accountHead")
@CrossOrigin("*")
@Slf4j
public class AccountController {

    @Autowired
    RestTemplate rt;

    @GetMapping("/get")
    public String log() {
        log.info("Health check endpoint hit: /get");
        return "success!";
    }

    @PutMapping("/loanDisburse/{CustomerId}/{transferAmount}")
    public ResponseEntity<String> loanDisbursement(@PathVariable("CustomerId") int CustomerId,
                                                   @PathVariable("transferAmount") double transferAmount) {
        log.info("Received request to disburse loan for Customer ID: {} with amount: {}", CustomerId, transferAmount);

        try {
            Customer customer = rt.getForObject("http://localhost:1003/loan/getCustomer/" + CustomerId, Customer.class);

            if (customer == null) {
                log.warn("Customer not found with ID: {}", CustomerId);
                return new ResponseEntity<>("Customer not found!", HttpStatus.NOT_FOUND);
            }

            if (customer.getLoanDisbursement() == null) {
                customer.setLoanDisbursement(new LoanDisbursement());
                log.info("Initialized LoanDisbursement for Customer ID: {}", CustomerId);
            }

            customer.getLoanDisbursement().setTransferAmount(transferAmount);

            if (CustomerId == customer.getCustomerId()) {
                String url = "http://localhost:1003/loan/loanDisburse/" + CustomerId;
                rt.put(url, customer);
                log.info("Loan disbursement processed successfully for Customer ID: {}", CustomerId);
                return new ResponseEntity<>("Loan Disbursement Successfully Processed", HttpStatus.OK);
            } else {
                log.warn("Customer ID mismatch: PathVariable ID = {}, Retrieved ID = {}", CustomerId, customer.getCustomerId());
            }

        } catch (Exception e) {
            log.error("Error processing loan disbursement for Customer ID: {}", CustomerId, e);
            return new ResponseEntity<>("Internal server error while processing loan disbursement", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Customer not found...!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/ledgerGeneration/{CustomerId}/{payment}")
    public ResponseEntity<Customer> ledgerGeneration(@PathVariable("CustomerId") int CustomerId,
                                                     @PathVariable("payment") Double payment) {
        log.info("Received request to generate ledger for Customer ID: {} with payment: {}", CustomerId, payment);

        try {
            String url = "http://localhost:1003/loan/legergen/" + CustomerId + "/" + payment;
            ResponseEntity<Customer> response = rt.getForEntity(url, Customer.class);

            Customer customer = response.getBody();
            if (customer != null) {
                log.info("Ledger generated successfully for Customer ID: {}", CustomerId);
                return new ResponseEntity<>(customer, HttpStatus.CREATED);
            } else {
                log.warn("No customer data returned from ledger generation for ID: {}", CustomerId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error("Error generating ledger for Customer ID: {}", CustomerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
