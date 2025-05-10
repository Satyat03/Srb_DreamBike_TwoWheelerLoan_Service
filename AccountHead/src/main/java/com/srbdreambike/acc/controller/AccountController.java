 	package com.srbdreambike.acc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.srbdreambike.acc.model.Customer;

@RestController
@RequestMapping("/accountHead")
@CrossOrigin("*")
public class AccountController {
	
	
	@Autowired
	RestTemplate rt;
	
	@GetMapping("/get")
	public String log() {
		return "success!";
	}
	
	@PutMapping("/loanDisburse/{CustomerId}/{transferAmount}")
	public ResponseEntity<String> loanDisbursement( @PathVariable("CustomerId") int CustomerId,@PathVariable("transferAmount") double transferAmount) {

		Customer customer = rt.getForObject("http://localhost:1003/loan/getCustomer/"+CustomerId, Customer.class);
		customer.getLoanDisbursement().setTransferAmount(transferAmount);
		if(CustomerId==customer.getCustomerId()) {
		String url = "http://localhost:1003/loan/loanDisburse/" + CustomerId;

		rt.put(url, customer);
		return new ResponseEntity<String>("Loan Disbursement Successfully Processed",HttpStatus.OK);
		}

		return new ResponseEntity<String>("customer not found...!", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/ledgerGeneration/{CustomerId}/{payment}")
	public ResponseEntity<Customer> ledgerGeneration(@PathVariable("CustomerId") int CustomerId ,@PathVariable("payment") Double payment){
		String url = "http://localhost:1003/loan/legergen/"+CustomerId+"/"+payment;
		ResponseEntity<Customer> object = rt.getForEntity(url, Customer.class);
		Customer customer = object.getBody();
		return new ResponseEntity<Customer>(customer,HttpStatus.CREATED);
		
	}
	
	
	
	
}
