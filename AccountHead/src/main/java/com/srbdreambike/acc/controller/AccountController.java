package com.srbdreambike.acc.controller;

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

@RestController
@RequestMapping("/accountHead")
public class AccountController {
	
	
	@Autowired
	RestTemplate rt;
	
	@GetMapping("/get")
	public String log() {
		return "success!";
	}
	
	@PutMapping("/loanDisburse/{CustomerId}")
	public ResponseEntity<String> loanDisbursement(@RequestBody Customer c, @PathVariable int CustomerId) {

		String url = "http://localhost:1003/loan/loanDisburse/" + CustomerId;

		rt.put(url, c);

		return new ResponseEntity<String>("Loan Disbursement Successfully Processed!", HttpStatus.CREATED);
	}
	
	
	
	
}
