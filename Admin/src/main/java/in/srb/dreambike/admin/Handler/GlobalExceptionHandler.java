package in.srb.dreambike.admin.Handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.srb.dreambike.admin.Exception.FileInvalidException;
import in.srb.dreambike.admin.Exception.InvalidFormatException;
import in.srb.dreambike.admin.dto.UserResponse;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler(FileInvalidException.class)
	public ResponseEntity<UserResponse> handelGlobalException(FileInvalidException exception)
	{
		String message= exception.getMessage();
		
		UserResponse user= new UserResponse(message, new Date());
	
		return new ResponseEntity<UserResponse>(user, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<UserResponse> handleException(InvalidFormatException formatException)
	{
		String msg= formatException.getMessage();
		
		UserResponse response= new UserResponse(msg, new Date());
		
		return new ResponseEntity<UserResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		
	}
}
