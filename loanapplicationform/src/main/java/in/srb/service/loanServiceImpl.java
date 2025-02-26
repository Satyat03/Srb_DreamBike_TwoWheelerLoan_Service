package in.srb.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.srb.exception.FileInvalideException;
import in.srb.exception.InvalidAadharcardException;
import in.srb.exception.InvalidAddressProofException;
import in.srb.exception.InvalidCheckException;
import in.srb.exception.InvalidIncomeTaxException;
import in.srb.exception.InvalidPancardException;
import in.srb.exception.InvalidSalarySlipException;
import in.srb.exception.SignatureInvalidException;
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

			 if (photo != null || !photo.isEmpty() || isValidImage(photo))
			 {
				 pd.setPhoto(img); 
				 
			 }	
			 else 
			 { 
				 throw new FileInvalideException("Photo must be a valid image file (JPEG/JPG).");
		      }	
			 
			 if(!signature.isEmpty() && isValidImage(signature))
			 {
					pd.setSignature(sig);

			 }
			 else {
				 throw new SignatureInvalidException("Signature must be a valid image file (JPEG/JPG)");
			 }
			 
			 if(pancard !=null && isValidPDF(panCard))
			 {
					pd.setPanCard(pancard);

			 }
			 else 
			 {throw new InvalidPancardException("Pancard should be in pdf format and file name must be in lower case (e.g- pancard.pdf)");}
			
			 if(!addharCard.isEmpty() && isValidPDF(addharCard))
			 {
					pd.setAddharCard(adharcard);

			 }
			 else {throw new InvalidAadharcardException("Adhar card should be in PDF only and file name must be in lower case(adharcard.pdf)");}
			 
			 if(addressProof.isEmpty() && isValidPDF(addressProof))
			 {
					pd.setAddressProof(addr);
			 }
			 else {throw new InvalidAddressProofException("Address proof should be in PDF only and file name must be in lower case(address.pdf)");}
			
			 if(!incomeTax.isEmpty() && isValidPDF(incomeTax))
			 {
					pd.setIncomeTax(incometax);
			 }
			 else {throw new InvalidIncomeTaxException("Income Certificate should be in PDF only and file name must be in lower case(income.pdf)");}
			 if(!bankCheque.isEmpty() && isValidPDF(bankCheque))
			 {
					pd.setBankCheque(check);

			 }
			 else
			 {
				 throw new InvalidCheckException("Bank Check should be in PDF only and file name must be in lower case(bankcheck.pdf)");
			 }
			 
			 if(!salarySlips.isEmpty() && isValidPDF(salarySlips))
			 {
					pd.setSalarySlips(slip);
			 }
			 else
			 {
				 throw new InvalidSalarySlipException("salary slip should be in PDF only and file name must be in lowercase(salaryslip.pdf)");
			 }
//			 pd.setPhoto(img);
//			pd.setAddressProof(addr);
//			pd.setPanCard(pancard);
//			pd.setIncomeTax(incometax);
//			pd.setAddharCard(adharcard);
//			pd.setSignature(sig);
//			pd.setBankCheque(check);
//			pd.setSalarySlips(slip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		c.setAllPersonalDocument(pd);

		Customer save = lr.save(c);

		return save;
	}


	private boolean isValidImage(MultipartFile photo) 
	{
		String str=photo.getContentType();
		
		return str !=null && (str.endsWith("jpeg/jpg"));
	}


	private boolean isValidPDF(MultipartFile file) 
	{
	    if (file != null) 
	    {
	        String contentType = file.getContentType();
	        String filename = file.getOriginalFilename();

	        // Check MIME type and file extension for PDF validation
	        return (contentType != null && contentType.equals("application/pdf")) ||
	               (filename != null && filename.toLowerCase().endsWith(".pdf"));
	    }
	    return false;

	}
}
