package in.srb.dreambiketwowheelerloan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	@GetMapping("/get")
	public String message()
	{
		return "success";
	}
	
}
