package in.srb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import in.srb.model.Customer;

public interface LoanServiceI {

	//public Customer saveData(Customer c);

	public Customer saveData(Customer c, MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard,
			MultipartFile addressProof,MultipartFile photo, MultipartFile signature, MultipartFile bankCheque, MultipartFile salarySlips);

	public List<Customer> getAllCustomer(String loanStatus);

	public Optional<Customer> findById(Integer customerId);


}
