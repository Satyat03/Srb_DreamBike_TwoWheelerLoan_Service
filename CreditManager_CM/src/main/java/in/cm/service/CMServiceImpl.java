package in.cm.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cm.model.Customer;
import in.cm.model.SanctionLetter;
import in.cm.repo.CMrepo;

@Service
public class CMServiceImpl implements CMserviceI{

	@Autowired
    CMrepo cmr;
	
	
	@Override
	public void updateSanction(int customerId, Customer cs) {
		
//		cmr.save(cs);
//		
//		Optional<Customer> byId = cmr.findById(customerId);
//		
//		if(byId.isPresent())
//		{
//			Customer c = byId.get();
//			c.getSl().setSanctionDate(new Date());
//			c.getSl().setApplicantName(c.getCustomerName());
//			c.getSl().setContactDetails(c.getCustomerMobileNumber());
//			c.getSl().setLoanRequired(c.getCustomerTotalLoanRequired());
//		
//		if(c.getCibilScore().getCibilScore() >= 800)
//		{
//			c.getSl().setLoanAmtSanctioned(c.getSl().getLoanRequired());
//			c.getSl().setRateOfInterest(6.5f);
//		} else if(c.getCibilScore().getCibilScore() >= 650)
//		{
//			c.getSl().setLoanAmtSanctioned(0.75*c.getSl().getLoanRequired());
//			c.getSl().setRateOfInterest(8.5f);
//		}else if(c.getCibilScore().getCibilScore() >= 550)
//		{
//			c.getSl().setLoanAmtSanctioned(0.50*c.getSl().getLoanRequired());
//			c.getSl().setRateOfInterest(10.5f);
//		}else if(c.getCibilScore().getCibilScore() >= 300)
//		{
//			c.getSl().setLoanAmtSanctioned(0.25*c.getSl().getLoanRequired());
//			c.getSl().setRateOfInterest(12.5f);
//		}
//		
//		
//		c.getSl().setMonthlyEmiAmount((c.getSl().getLoanAmtSanctioned()*c.getSl().getRateOfInterest()*Math.pow(1+(c.getSl().getRateOfInterest()/12),
//				c.getSl().getLoanTenureInYear()*12))
//				/(Math.pow(1+(c.getSl().getRateOfInterest()/12), c.getSl().getLoanTenureInYear()*12))-1);
//		c.getSl().setInterestType("simple Interest");
//		
//		c.getSl().setModeOfPayment("Online");
//		c.getSl().setStatus("Created");
//		}
//	}
	
	      
	        Optional<Customer> byId = cmr.findById(customerId);

	        if (byId.isPresent()) {
	            Customer c = byId.get();

	            // Ensure SanctionLetter and CibilScore objects exist
	          if (c.getSl() == null) {
	                c.setSl(new SanctionLetter());  // Create new if null
	            }

	            if (c.getCibilScore() == null) {
	                throw new IllegalStateException("CibilScore not found for customer ID: " + customerId);
	            }

	            // Set basic sanction details
	            c.getSl().setSanctionDate(new Date());
	            c.getSl().setApplicantName(c.getCustomerName());
	            c.getSl().setContactDetails(c.getCustomerMobileNumber());
	            c.getSl().setLoanRequired(c.getCustomerTotalLoanRequired());

	            double sanctionedAmount = 0;
	            float interestRate = 0;
	            int cibilScore = c.getCibilScore().getCibilScore();

	            // Loan approval logic based on CIBIL score
	            if (cibilScore >= 800) {
	                sanctionedAmount = c.getSl().getLoanRequired();
	                interestRate = 6.5f;
	            } else if (cibilScore >= 650) {
	                sanctionedAmount = 0.75 * c.getSl().getLoanRequired();
	                interestRate = 8.5f;
	            } else if (cibilScore >= 550) {
	                sanctionedAmount = 0.50 * c.getSl().getLoanRequired();
	                interestRate = 10.5f;
	            } else if (cibilScore >= 300) {
	                sanctionedAmount = 0.25 * c.getSl().getLoanRequired();
	                interestRate = 12.5f;
	            } else {
	                throw new IllegalStateException("Cibil Score too low for loan sanction.");
	            }

	            c.getSl().setLoanAmtSanctioned(sanctionedAmount);
	            c.getSl().setRateOfInterest(interestRate);

	            // Correct EMI calculation formula
	            double P = sanctionedAmount;  // Loan Amount
	            double r = (interestRate / 12) / 100;  // Monthly Interest Rate
	            int n = cs.getSl().getLoanTenureInYear() * 12;  // Number of Months

	            if (n > 0 && r > 0) {
	                double emi = (P * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
	                c.getSl().setMonthlyEmiAmount(emi);
	            } else {
	                c.getSl().setMonthlyEmiAmount(0);  // Prevent division by zero
	            }

	            // Set other sanction details
	            c.getSl().setInterestType("Compound Interest");
	            c.getSl().setModeOfPayment("Online");
	            c.getSl().setStatus("Created");
	            c.getSl().setLoanTenureInYear(cs.getSl().getLoanTenureInYear());

	            // Save updated customer details in the database
	            cmr.save(c);
	            
	        } else {
	            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
	        }
	    }

	}



