package in.cm.service;

import in.cm.model.Customer;
import in.cm.model.SanctionLetter;

public interface EmailService
{
 
	public SanctionLetter sendSantionLetterMail(Customer customerDetails);
	
}
