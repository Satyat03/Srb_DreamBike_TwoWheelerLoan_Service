package com.srbdreambike.acc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.srbdreambike.acc.model.Customer;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/accountHead")
@Slf4j
public class AccountController {
	   
	
	@Autowired
	RestTemplate rt;
	
	
	@GetMapping("/get")
	public String log() {
		return "success!";
	}
	
	@PutMapping("/loanDisburse/{CustomerId}")
	public ResponseEntity<String> loanDisbursement(@RequestBody Customer c, @PathVariable ("CustomerId")int CustomerId) {

		String url = "http://localhost:1003/loan/loanDisburse/" + CustomerId;

		rt.put(url, c);
		
		log.error("this is error event");
		log.info("this is info event");
	    log.warn("this is warn event");
	    log.debug("this is debug event");
				

		return new ResponseEntity<String>("Loan Disbursement Successfully Processed!", HttpStatus.CREATED);
	}
	

	@GetMapping("/getAllCustomerDisburse")
	public ResponseEntity<List<Customer>> getAllLoanDisburse(){
		
		String url="http://localhost:1003/loan/getAllCustomer/Disbursted";
		List<Customer> list = rt.getForObject(url, List.class);
		return new ResponseEntity<List<Customer>>(list,HttpStatus.OK);
		
	}

}
