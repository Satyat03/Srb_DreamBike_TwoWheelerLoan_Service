package in.srb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email 
{
	private String toEmail;
	private String fromEmail;
	private String subject;
	private String text;
}
