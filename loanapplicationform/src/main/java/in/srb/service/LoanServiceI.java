package in.srb.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.srb.model.Customer;

public interface LoanServiceI {

	//public Customer saveData(Customer c);

	public Customer saveData(Customer c, MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard,
			MultipartFile photo, MultipartFile signature, MultipartFile bankCheque, MultipartFile salarySlips);

	public List<Customer> getAllCustomer(String loanStatus);

}
