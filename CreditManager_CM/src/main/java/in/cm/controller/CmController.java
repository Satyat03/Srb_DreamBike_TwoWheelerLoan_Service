package in.cm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cm.model.Customer;
import in.cm.service.CMserviceI;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cm")
@Slf4j
public class CmController {

	@Autowired
	CMserviceI cmi;

	@GetMapping("/get")
	public String log() {
		return "success!";
	}

	@PutMapping("/addsanction/{CustomerId}")
	public ResponseEntity<Customer> updatesanction(@PathVariable("CustomerId") int CustomerId,
			@RequestBody Customer cs) {

		cmi.updateSanction(CustomerId, cs);

		log.error("this is error log");
		log.info("this is info log");
		log.warn("this is warn log");
		log.debug("this is debug log");
		


		return null;

	}

}
