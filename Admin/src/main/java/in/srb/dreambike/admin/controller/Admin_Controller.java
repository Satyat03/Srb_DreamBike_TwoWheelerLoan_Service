package in.srb.dreambike.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/admin")
@RestController
public class Admin_Controller {

	
	@GetMapping("/get")
	public String getdata()
	{
		
		return "Success";
		
	}
	
}
