package in.srb.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.srb.dto.UserResponse;
import in.srb.exception.FileInvalideException;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler(FileInvalideException.class)
	public ResponseEntity<UserResponse> handleGlobalException(FileInvalideException exception)
	{
		String message=exception.getMessage();
		
		UserResponse us= new UserResponse(message, new Date());
		
		return new ResponseEntity<UserResponse>(us, HttpStatus.BAD_REQUEST);
	}
}
