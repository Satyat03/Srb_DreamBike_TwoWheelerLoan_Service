package in.cm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cm")
public class CmController {

	@GetMapping("/get")
	public String log() {
		return "success!";
	}
}
