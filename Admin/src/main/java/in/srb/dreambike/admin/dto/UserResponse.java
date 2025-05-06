package in.srb.dreambike.admin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserResponse 
{

	public String message;
	public Date timestamp;
	
	public UserResponse(String message, Date timestamp) {
		super();
		this.message = message;
		this.timestamp = timestamp;
	}

	
}
