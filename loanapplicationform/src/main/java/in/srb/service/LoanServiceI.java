package in.srb.service;

import org.springframework.web.multipart.MultipartFile;

import in.srb.model.Customer;

public interface LoanServiceI {

	//public Customer saveData(Customer c);

	public Customer saveData(String jsonData, MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard,
			MultipartFile photo, MultipartFile signature, MultipartFile bankCheque, MultipartFile salarySlips);

}
