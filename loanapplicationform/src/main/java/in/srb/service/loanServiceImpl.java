
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
<<<<<<< HEAD
	public Customer saveData(Customer c,MultipartFile addressProof, MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard,
=======
	public Customer saveData(Customer c, MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard,
>>>>>>> branch 'Bhavishya' of https://github.com/Satyat03/Srb_DreamBike_TwoWheelerLoan_Service.git
			MultipartFile photo, MultipartFile signature, MultipartFile bankCheque, MultipartFile salarySlips) {
<<<<<<< HEAD
		
		AllPersonalDocuments pd=new AllPersonalDocuments();
	
=======

		AllPersonalDocuments pd = new AllPersonalDocuments();

>>>>>>> branch 'Bhavishya' of https://github.com/Satyat03/Srb_DreamBike_TwoWheelerLoan_Service.git
		try {
			byte[] incometax = incomeTax.getBytes();
<<<<<<< HEAD
		    byte[] address = addressProof.getBytes();
=======
>>>>>>> branch 'Bhavishya' of https://github.com/Satyat03/Srb_DreamBike_TwoWheelerLoan_Service.git
			byte[] pancard = panCard.getBytes();
			byte[] adharcard = addharCard.getBytes();
			byte[] sig = signature.getBytes();
			byte[] check = bankCheque.getBytes();
			byte[] slip = salarySlips.getBytes();

			pd.setPanCard(pancard);
			pd.setAddressProof(address);
			pd.setIncomeTax(incometax);
			pd.setAddharCard(adharcard);
			pd.setSignature(sig);
			pd.setBankCheque(check);
			pd.setSalarySlips(slip);
<<<<<<< HEAD
			
			
			
=======
>>>>>>> branch 'Bhavishya' of https://github.com/Satyat03/Srb_DreamBike_TwoWheelerLoan_Service.git
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
<<<<<<< HEAD
		
		c.setAllPersonalDocument(pd);
		
		Customer save = lr.save(c);
		
		
=======

		c.setAllPersonalDocument(pd);

		Customer save = lr.save(c);

>>>>>>> branch 'Bhavishya' of https://github.com/Satyat03/Srb_DreamBike_TwoWheelerLoan_Service.git
		return save;
	}

}
