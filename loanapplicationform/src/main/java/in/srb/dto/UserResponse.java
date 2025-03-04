package in.srb.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserResponse 
{

	private String msg;
	private Date timestamp;
	
	public UserResponse(String msg, Date timestamp) 
	{
		super();
		this.msg = msg;
		this.timestamp = timestamp;
	}

	public UserResponse() 
	{
		super();
	}
	
	
}
