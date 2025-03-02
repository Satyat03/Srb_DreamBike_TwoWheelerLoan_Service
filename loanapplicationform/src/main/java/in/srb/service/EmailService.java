package in.srb.service;

import in.srb.model.Customer;
import in.srb.model.SanctionLetter;

public interface EmailService
{
 
	public SanctionLetter sendSantionLetterMail(Customer customerDetails);
}
