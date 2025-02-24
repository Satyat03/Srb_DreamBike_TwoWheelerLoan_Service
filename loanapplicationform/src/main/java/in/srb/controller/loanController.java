package in.srb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
public class loanController {

	@GetMapping("/get")
	public String get()
	{
		return "success";
	}
}
