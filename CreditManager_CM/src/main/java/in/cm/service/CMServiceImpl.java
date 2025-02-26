package in.cm.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cm.model.Customer;
import in.cm.repo.CMrepo;

@Service
public class CMServiceImpl implements CMserviceI{

	@Autowired
    CMrepo cmr;
	
	
	@Override
	public void updateSanction(int customerId, Customer cs) {
		
		Optional<Customer> byId = cmr.findById(customerId);
		
		if(byId.isPresent())
		{
			Customer c = byId.get();
			c.getSl().setSanctionDate(new Date());
			c.getSl().setApplicantName(c.getCustomerName());
			c.getSl().setContactDetails(c.getCustomerMobileNumber());
			c.getSl().setLoanRequired(c.getCustomerTotalLoanRequired());
		
		if(c.getCibilScore().getCibilScore() >= 800)
		{
			c.getSl().setLoanAmtSanctioned(c.getSl().getLoanRequired());
			c.getSl().setRateOfInterest(6.5f);
		} else if(c.getCibilScore().getCibilScore() >= 650)
		{
			c.getSl().setLoanAmtSanctioned(0.75*c.getSl().getLoanRequired());
			c.getSl().setRateOfInterest(8.5f);
		}else if(c.getCibilScore().getCibilScore() >= 550)
		{
			c.getSl().setLoanAmtSanctioned(0.50*c.getSl().getLoanRequired());
			c.getSl().setRateOfInterest(10.5f);
		}else if(c.getCibilScore().getCibilScore() >= 300)
		{
			c.getSl().setLoanAmtSanctioned(0.25*c.getSl().getLoanRequired());
			c.getSl().setRateOfInterest(12.5f);
		}
		
		
		c.getSl().setMonthlyEmiAmount((c.getSl().getLoanAmtSanctioned()*c.getSl().getRateOfInterest()*Math.pow(1+(c.getSl().getRateOfInterest()/12),
				c.getSl().getLoanTenureInYear()*12))
				/(Math.pow(1+(c.getSl().getRateOfInterest()/12), c.getSl().getLoanTenureInYear()*12))-1);
		c.getSl().setInterestType("simple Interest");
		
		c.getSl().setModeOfPayment("Online");
		c.getSl().setStatus("Created");
		}
	}

}
