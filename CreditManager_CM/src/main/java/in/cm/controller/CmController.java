package in.cm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cm.model.Customer;
import in.cm.service.CMserviceI;


@RestController
@RequestMapping("/cm")
@CrossOrigin("*")
public class CmController {

	@Autowired
	CMserviceI cmi;
	
	
	@GetMapping("/get")
	public String log() {
		return "success!";
	}
		
		@PutMapping("/addsanction/{CustomerId}/{tenure}")
		public ResponseEntity<Customer> updatesanction(@PathVariable("CustomerId") int CustomerId, @PathVariable("tenure") int tenure)
		{
			

			Customer customer=cmi.updateSanction(CustomerId,tenure);

			//Customer updateSanction = cmi.updateSanction(CustomerId,cs);

			return new ResponseEntity<Customer>(customer,HttpStatus.OK);

			
		}
			
			
			
			
			
			
		
	
		
		
		
		
		
		
		
		
		
	}

