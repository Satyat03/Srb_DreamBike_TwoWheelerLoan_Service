package in.srb.service;

import java.io.IOException;

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
	public Customer saveData(String jsonData, MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard,
			MultipartFile photo, MultipartFile signature, MultipartFile bankCheque, MultipartFile salarySlips) {
		
		ObjectMapper ob=new ObjectMapper();
		
		AllPersonalDocuments pd=new AllPersonalDocuments();
		
		Customer customer=null;
		try {

			
			customer = ob.readValue(jsonData,Customer.class);
			System.out.println(customer);
			byte[] pancard = panCard.getBytes();
			byte[] incometax = incomeTax.getBytes();
			byte[] adharcard = addharCard.getBytes();
			byte[] sig = signature.getBytes();
			byte[] check = bankCheque.getBytes();
			byte[] slip = salarySlips.getBytes();
			pd.setPanCard(pancard);
			pd.setIncomeTax(incometax);
			pd.setAddharCard(adharcard);
			pd.setSignature(sig);
			pd.setBankCheque(check);
			pd.setSalarySlips(slip);
			
			customer.setAllPersonalDocument(pd);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Customer save = lr.save(customer);
		
		
		return save;
	}

}
