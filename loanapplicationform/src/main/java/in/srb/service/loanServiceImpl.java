package in.srb.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.srb.model.AllPersonalDocuments;
import in.srb.model.Customer;
import in.srb.repo.LoanRepo;

@Service
public class loanServiceImpl implements LoanServiceI {

	@Autowired
	LoanRepo lr;

//	@Transactional
//	@Override
//	public Customer saveData(Customer c) {
//		Customer customer = lr.save(c);
//		System.out.println(customer.toString());
//		return customer;
//	}

	@Override
	public Customer saveData(Customer c, MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard,
			MultipartFile addressProof,MultipartFile photo, MultipartFile signature, MultipartFile bankCheque, MultipartFile salarySlips) {

		AllPersonalDocuments pd = new AllPersonalDocuments();

		try {
			byte[] incometax = incomeTax.getBytes();
			byte[] addr = addressProof.getBytes();
			byte[] pancard = panCard.getBytes();
			byte[] adharcard = addharCard.getBytes();
			byte[] sig = signature.getBytes();
			byte[] check = bankCheque.getBytes();
			byte[] slip = salarySlips.getBytes();
			byte[] img = photo.getBytes();

			pd.setPhoto(img);
			pd.setAddressProof(addr);
			pd.setPanCard(pancard);
			pd.setIncomeTax(incometax);
			pd.setAddharCard(adharcard);
			pd.setSignature(sig);
			pd.setBankCheque(check);
			pd.setSalarySlips(slip);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		c.setAllPersonalDocument(pd);

		Customer save = lr.save(c);

		return save;
	}

	@Override
	public List<Customer> getAllCustomer(String loanStatus) {
		
		return lr.findAllByLoanStatus(loanStatus);
	}

	@Override
	public Customer getCustomer(String username, String password) {
	    Optional<Customer> optionalCustomer = lr.findByUsername(username);

	    if (optionalCustomer.isEmpty()) {
	    	//Need to create custom exception class
	        throw new IllegalArgumentException("Your username is incorrect.");
	    }

	    Customer customer = optionalCustomer.get();

	    if (!password.equals( customer.getPassword())) {
	        throw new IllegalArgumentException("Your password is incorrect.");
	    
    }

	    customer.setLoanStatus("Sanctioned");
	    customer.getSl().setStatus("Accepted");

	    return lr.save(customer);
	    
	}


}
