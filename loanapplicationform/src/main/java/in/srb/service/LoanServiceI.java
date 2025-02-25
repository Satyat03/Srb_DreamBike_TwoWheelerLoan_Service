package in.srb.service;

import org.springframework.web.multipart.MultipartFile;

import in.srb.model.Customer;

public interface LoanServiceI {

	//public Customer saveData(Customer c);

<<<<<<< HEAD
	public Customer saveData(Customer c , MultipartFile addressProof,MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard,
=======
	public Customer saveData(Customer c, MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard,
>>>>>>> branch 'Bhavishya' of https://github.com/Satyat03/Srb_DreamBike_TwoWheelerLoan_Service.git
			MultipartFile photo, MultipartFile signature, MultipartFile bankCheque, MultipartFile salarySlips);

}
