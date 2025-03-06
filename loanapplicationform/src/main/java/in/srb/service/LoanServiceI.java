package in.srb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import in.srb.model.Customer;

public interface LoanServiceI {

	//public Customer saveData(Customer c);

	public Customer saveData(Customer c, MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard,
			MultipartFile addressProof,MultipartFile photo, MultipartFile signature, MultipartFile bankCheque, MultipartFile salarySlips) throws Exception;

	public List<Customer> getAllCustomer(String loanStatus);


	public Customer getCustomer(String username, String password);

	public Optional<Customer> findById(Integer customerId);

	public String updateLoanDisbursement(Customer c, int customerId);

	public void savecustomer(Customer customer);

	public Customer changestatus(int customerId);

	public Customer createleager(int customerId, Double payment) throws Exception;



}
