package in.srb.dreambiketwowheelerloan.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.srb.dreambiketwowheelerloan.dto.CustomerResponce;
import in.srb.dreambiketwowheelerloan.exception.AdharCardInvalidException;
import in.srb.dreambiketwowheelerloan.exception.AgeMisMatchException;
import in.srb.dreambiketwowheelerloan.exception.CaseMisMatchException;
import in.srb.dreambiketwowheelerloan.exception.EmailInvalidException;
import in.srb.dreambiketwowheelerloan.exception.MobileNoInvalidException;
import in.srb.dreambiketwowheelerloan.exception.PanCardInvalidException;

@RestControllerAdvice
public class GlobalException {

	
	@ExceptionHandler(CaseMisMatchException.class)
	public ResponseEntity<CustomerResponce> handleCaseMisMatchException(CaseMisMatchException ae){
		String message = ae.getMessage();
		CustomerResponce ur=new CustomerResponce(message,new Date());
	    
        return new ResponseEntity<CustomerResponce>(ur,HttpStatus.BAD_REQUEST);	
	}
	
	@ExceptionHandler(AgeMisMatchException.class)
	public ResponseEntity<CustomerResponce> handleAgeMisMatchException(AgeMisMatchException ae){
		String message = ae.getMessage();
		CustomerResponce ur=new CustomerResponce(message,new Date());
	    
        return new ResponseEntity<CustomerResponce>(ur,HttpStatus.BAD_REQUEST);	
	}
	@ExceptionHandler(EmailInvalidException.class)
	public ResponseEntity<CustomerResponce> handleEmailMatchException (EmailInvalidException ee){
		String message = ee.getMessage();
		CustomerResponce ur=new CustomerResponce(message,new Date());
	    
        return new ResponseEntity<CustomerResponce>(ur,HttpStatus.BAD_REQUEST);	
	}
	@ExceptionHandler(PanCardInvalidException.class)
	public ResponseEntity<CustomerResponce> handlePanCardMatchException (PanCardInvalidException pe){
		String message = pe.getMessage();
		CustomerResponce ur=new CustomerResponce(message,new Date());
	    
        return new ResponseEntity<CustomerResponce>(ur,HttpStatus.BAD_REQUEST);	
	}
	@ExceptionHandler(MobileNoInvalidException.class)
	public ResponseEntity<CustomerResponce> handleMobNoMatchException (MobileNoInvalidException me){
		String message = me.getMessage();
		CustomerResponce ur=new CustomerResponce(message,new Date());
	    
        return new ResponseEntity<CustomerResponce>(ur,HttpStatus.BAD_REQUEST);	
	}
	@ExceptionHandler(AdharCardInvalidException.class)
	public ResponseEntity<CustomerResponce> handleAdharCardMatchException (AdharCardInvalidException ae){
		String message = ae.getMessage();
		CustomerResponce ur=new CustomerResponce(message,new Date());
	    
        return new ResponseEntity<CustomerResponce>(ur,HttpStatus.BAD_REQUEST);	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
