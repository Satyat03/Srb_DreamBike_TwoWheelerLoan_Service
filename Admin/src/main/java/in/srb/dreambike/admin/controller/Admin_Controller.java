package in.srb.dreambike.admin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.srb.dreambike.admin.model.AdminDetails;
import in.srb.dreambike.admin.service.AdminServiceI;


@RequestMapping("/admin")
@RestController
public class Admin_Controller {

	@Autowired
	AdminServiceI serviceI;
	
	@GetMapping("/get")
	public String getdata()
	{
		
		return "Success";
		
	}
	
	@PostMapping("/addEmployee")
	public AdminDetails addData(@RequestParam ("adminJson")String adminJson, 
						  @RequestPart ("empImage") MultipartFile  empImage,
						  @RequestPart ("empPancard")MultipartFile empPancard) throws IOException
	{
		AdminDetails details=serviceI.saveData(adminJson, empImage, empPancard);
		
		return details;
	}
	
}
