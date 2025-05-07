package in.srb.dreambike.admin.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@CrossOrigin("*")
public class Admin_Controller {

	@Autowired
	AdminServiceI serviceI;
	
	@GetMapping("/get")
	public String getdata()
	{
		
		return "Success";
		
	}
	
	@PostMapping("/addEmployee")
	public ResponseEntity<AdminDetails> addData(@RequestParam ("adminJson")String adminJson, 
						  @RequestPart ("empImage") MultipartFile  empImage,
						  @RequestPart ("empPancard")MultipartFile empPancard) 
	{
		AdminDetails details=serviceI.saveData(adminJson, empImage, empPancard);
		
		return new ResponseEntity<AdminDetails>(details,HttpStatus.CREATED);
	}
	@GetMapping("/{empId}")
	public ResponseEntity<AdminDetails> getSingleData(@PathVariable("empId") int empId){
		
		AdminDetails ad=serviceI.getSingleEmp(empId);
		
		return new ResponseEntity<AdminDetails>(ad,HttpStatus.OK);
		
	}
	@GetMapping("/getAll")
	public ResponseEntity<List<AdminDetails>> getAllEmp(){
		List<AdminDetails> allEmp=serviceI.getAllEmpDetails();
		return new ResponseEntity<List<AdminDetails>>(allEmp,HttpStatus.OK);
		
	}
	@PutMapping(value="/{empId}", consumes = "multipart/form-data")
	public ResponseEntity<AdminDetails> updateEmp(
	        @PathVariable("empId") int empId,
	        @RequestParam("adminJson") String adminJson,
	        @RequestPart("empImage") MultipartFile empImage,
	        @RequestPart("empPancard") MultipartFile empPancard
	) {
	    AdminDetails updatedAdmin = serviceI.updateData(empId, adminJson, empImage, empPancard);
	    return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
	}
	
	@GetMapping("/adminLogin/{username}/{password}")
	public ResponseEntity<AdminDetails> userLogin(@PathVariable String username,
			@PathVariable String password){
		AdminDetails a=serviceI.loginCheck(username,password);
				return new ResponseEntity<AdminDetails>(a,HttpStatus.OK);
		
	}
	@DeleteMapping("/{empId}")
	public ResponseEntity<String> deleteEmp(@PathVariable int empId){
		serviceI.deleteData(empId);
		return new ResponseEntity<String>("Employee Deleted..!!",HttpStatus.OK);
		
	}

	
}
