package in.srb.dreambike.admin.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.srb.dreambike.admin.model.AdminDetails;
import in.srb.dreambike.admin.repo.AdminRepo;

@Service
public class AdminServiceImpl implements AdminServiceI {

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	AdminRepo adminRepo;

	@Override
	public AdminDetails saveData(String adminJson, MultipartFile empImage, MultipartFile empPancard) 
	{
		 try {
	            //converting json data to simple java Object
	            AdminDetails adminDetails = mapper.readValue(adminJson, AdminDetails.class);

	            
	            byte[] imageData = empImage.getBytes();
	            byte[] pancardData = empPancard.getBytes();

	            
	            adminDetails.setEmpImage(imageData);//settign the byte type image
	            adminDetails.setEmpPancard(pancardData);

	           
	          
	            return adminRepo.save(adminDetails);
		 }
		 
		 catch(IOException e)
		 {
			 e.printStackTrace();
		 }
		return null;
	         
		 
		
		

	}	
    
}
