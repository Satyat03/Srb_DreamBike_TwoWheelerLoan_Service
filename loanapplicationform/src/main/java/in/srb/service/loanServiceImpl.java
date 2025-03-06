package in.srb.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.srb.exception.FileInvalideException;
import in.srb.exception.InvalidAadharcardException;
import in.srb.exception.InvalidAddressProofException;
import in.srb.exception.InvalidCheckException;
import in.srb.exception.InvalidIncomeTaxException;
import in.srb.exception.InvalidPancardException;
import in.srb.exception.InvalidSalarySlipException;
import in.srb.exception.SignatureInvalidException;
import in.srb.model.AllPersonalDocuments;
import in.srb.model.Customer;
import in.srb.model.CustomerVerification;
import in.srb.model.Ledger;
import in.srb.model.LoanDisbursement;
import in.srb.repo.LoanRepo;

@Service
public class loanServiceImpl implements LoanServiceI {

	@Autowired
	LoanRepo lr;

	@Override
	public Customer saveData(Customer c, MultipartFile panCard, MultipartFile incomeTax, MultipartFile addharCard,
			MultipartFile addressProof, MultipartFile photo, MultipartFile signature, MultipartFile bankCheque,
			MultipartFile salarySlips) throws Exception {

		AllPersonalDocuments pd = new AllPersonalDocuments();

		byte[] incometax = incomeTax.getBytes();
		byte[] addr = addressProof.getBytes();
		byte[] pancard = panCard.getBytes();
		byte[] adharcard = addharCard.getBytes();
		byte[] sig = signature.getBytes();
		byte[] check = bankCheque.getBytes();
		byte[] slip = salarySlips.getBytes();
		byte[] img = photo.getBytes();

		if (isValidImage(photo)) {
			pd.setPhoto(img);

		} else {
			throw new FileInvalideException("Photo must be a valid image file (JPEG/JPG).");
		}

		if (isValidImage(signature)) {
			pd.setSignature(sig);

		} else {
			throw new SignatureInvalidException("Signature must be a valid image file (JPEG/JPG)");
		}

		if (isValidPDF(panCard)) {
			pd.setPanCard(pancard);

		} else {
			throw new InvalidPancardException(
					"Pancard should be in pdf format and file name must be in lower case (e.g- pancard.pdf)");
		}

		if (isValidPDF(addharCard)) {
			pd.setAddharCard(adharcard);

		} else {
			throw new InvalidAadharcardException(
					"Adhar card should be in PDF only and file name must be in lower case(adharcard.pdf)");
		}

		if (isValidPDF(addressProof)) {
			pd.setAddressProof(addr);
		} else {
			throw new InvalidAddressProofException(
					"Address proof should be in PDF only and file name must be in lower case(address.pdf)");
		}

		if (isValidPDF(incomeTax)) {
			pd.setIncomeTax(incometax);
		} else {
			throw new InvalidIncomeTaxException(
					"Income Certificate should be in PDF only and file name must be in lower case(income.pdf)");
		}

		if (isValidPDF(bankCheque)) {
			pd.setBankCheque(check);

		} else {
			throw new InvalidCheckException(
					"Bank Check should be in PDF only and file name must be in lower case(bankcheck.pdf)");
		}

		if (isValidPDF(salarySlips)) {
			pd.setSalarySlips(slip);
		} else {
			throw new InvalidSalarySlipException(
					"salary slip should be in PDF only and file name must be in lowercase(salaryslip.pdf)");
		}

		c.setAllPersonalDocument(pd);

		Customer save = lr.save(c);

		return save;
	}

	private boolean isValidImage(MultipartFile photo) {
		String filename = photo.getOriginalFilename();

		return filename != null
				&& (filename.toLowerCase().endsWith(".jpeg") || filename.toLowerCase().endsWith(".jpg"));
	}

	private boolean isValidPDF(MultipartFile file) {
		if (file != null && !file.isEmpty()) {
			String filename = file.getOriginalFilename(); // Get the original file name
			String contentType = file.getContentType(); // Get the MIME type

			// Log filename and content type for debugging
			System.out.println("File Name: " + filename);
			System.out.println("Content Type: " + contentType);

			// Validate file name and extension
			return (filename != null && filename.toLowerCase().endsWith(".pdf")
					&& filename.equals(filename.toLowerCase()));
		}
		return false;
	}

	// }
	@Override
	public List<Customer> getAllCustomer(String loanStatus) {

		return lr.findAllByLoanStatus(loanStatus);
	}

	@Override

	public Customer getCustomer(String username, String password) {
		Optional<Customer> optionalCustomer = lr.findByUsername(username);

		if (optionalCustomer.isEmpty()) {
			// Need to create custom exception class
			throw new IllegalArgumentException("Your username is incorrect.");
		}

		Customer customer = optionalCustomer.get();

		if (!password.equals(customer.getPassword())) {
			throw new IllegalArgumentException("Your password is incorrect.");

		}

		customer.setLoanStatus("Sanctioned");
		customer.getSl().setStatus("Accepted");

		return lr.save(customer);

	}

	public Optional<Customer> findById(Integer customerId) {
		Optional<Customer> byId = lr.findById(customerId);
		return byId;
	}

	@Override
	public String updateLoanDisbursement(Customer c, int customerId) {

		Random random = new Random();
		int loanNo = random.nextInt(1000, 9999);

		Optional<Customer> byId = lr.findById(customerId);
		if (byId.isPresent()) {
			Customer customer = byId.get();
			if (customer.getLoanDisbursement() == null) {
				customer.setLoanDisbursement(new LoanDisbursement());
			}
			customer.getLoanDisbursement().setLoanNo(loanNo);
			customer.getLoanDisbursement().setAgreementDate(new Date());
			customer.getLoanDisbursement().setAccountType(customer.getAccountDetails().getAccountType());
			customer.getLoanDisbursement().setTotalAmount(customer.getSl().getLoanAmtSanctioned());
			customer.getLoanDisbursement().setTransferAmount(c.getLoanDisbursement().getTransferAmount());
			customer.getLoanDisbursement().setAmountPaidDate(new Date());
			customer.getLoanDisbursement().setBankName(customer.getAccountDetails().getBankName());
			customer.getLoanDisbursement().setIfsccode(customer.getAccountDetails().getIfscCode());
			customer.getLoanDisbursement().setAccountNumber(customer.getAccountDetails().getAccountNumber());
			customer.getLoanDisbursement().setAmountPayType("Online");
			customer.getLoanDisbursement().setPaymentStatus("Disbursted");
			customer.setLoanStatus("Disbursted");

			lr.save(customer);

			return "Loan Disbustrment Successfully.....!";

		}
		return "Customer Id Not Found" + customerId;
	}

	@Override
	public void savecustomer(Customer customer) {
		lr.save(customer);

	}

	@Override
	public Customer changestatus(int customerId) {
		Optional<Customer> byId = lr.findById(customerId);
		if (byId.isPresent()) {
			Customer customer = byId.get();

			if (customer.getCustomerVerification() == null) {
				customer.setCustomerVerification(new CustomerVerification());
			}

			customer.setLoanStatus("Verified");
			customer.getCustomerVerification().setStatus("Checked");
			customer.getCustomerVerification().setRemarks("All documents verified");
			customer.getCustomerVerification().setVerificationDate(new Date());
			System.out.println(customer);
			// Save the updated customer entity
			return lr.save(customer);
		}
		// You can throw an exception or handle the null case gracefully
		throw new RuntimeException("Customer not found for ID: " + customerId);
	}

//	@Override
//	public Customer createleager(int customerId, int payment) {
//		 Optional<Customer> byId = lr.findById(customerId);
//		    if (byId.isPresent()) {
//		        Customer customer = byId.get();
//		        
//		        if (customer.getLedger() == null) {
//		            customer.setLedger(new ArrayList<>());
//		        }
//		        
//		     // Create a new Ledger entry
//		        Ledger ledger = new Ledger();
//		        ledger.setLedgerCreatedDate(new Date());
//		        ledger.setTotalLoanAmount(customer.getLoanDisbursement().getTotalAmount());
//
//		        Double principal = customer.getSl().getLoanAmtSanctioned(); // Loan amount
//		        Double rateOfInterest = (double) customer.getSl().getRateOfInterest(); // Example: Interest rate fetched from CibilScore
//		        Integer tenureInyear = customer.getSl().getLoanTenureInYear(); 
//		        
//		        if (principal == null || rateOfInterest == null || tenureInyear == null) {
//		            throw new RuntimeException("Required loan details are missing for Customer ID: " + customerId);
//		        }
//		        
//		        Double payableAmountWithInterest = principal + (principal * rateOfInterest * tenureInyear);
//		        
//		        ledger.setPayableAmountwithInterest(payableAmountWithInterest);
//		        ledger.setTenure(customer.getSl().getLoanTenureInYear());
//		        ledger.setMonthlyEMI(customer.getSl().getMonthlyEmiAmount());
//		        
//		     // Calculate the total amount paid till date
//		        double amountPaidTillDate = customer.getLedger().stream()
//		                .mapToDouble(Ledger::getAmountPaidtillDate) // Summing up previous payments
//		                .sum();
//		        amountPaidTillDate += payment;
//		        
//		        ledger.setAmountPaidtillDate(amountPaidTillDate);
//		        
//		     // Calculate remaining amount
//		        Double remainingAmount = payableAmountWithInterest - amountPaidTillDate;
//		        ledger.setRemainingAmount(remainingAmount);
//		        
//		        
//		    }
//		
//		
//		
//		
//		return null;
//	}

//	
//	public Customer createleager(int customerId, int payment) {
//	    Optional<Customer> byId = lr.findById(customerId);
//	    if (byId.isPresent()) {
//	        Customer customer = byId.get();
//
//	        // Initialize the ledger list if null
//	        if (customer.getLedger() == null) {
//	            customer.setLedger(new ArrayList<>());
//	        }
//
//	        // Create a new Ledger entry
//	        Ledger ledger = new Ledger();
//	        ledger.setLedgerCreatedDate(LocalDate.now().toString());
//	        ledger.setTotalLoanAmount(customer.getLoanDisbursement().getTotalAmount());
//
//	        // Fetch loan details
//	        Double principal = customer.getSl().getLoanAmtSanctioned(); // Loan amount
//	        Double rateOfInterest = (double) customer.getSl().getRateOfInterest(); // Example: Interest rate fetched from sanctioned loan
//	        Integer tenureInYear = customer.getSl().getLoanTenureInYear();
//
//	        if (principal == null || rateOfInterest == null || tenureInYear == null) {
//	            throw new RuntimeException("Required loan details are missing for Customer ID: " + customerId);
//	        }
//
//	        // Calculate payable amount with interest
//	        Double payableAmountWithInterest = principal + (principal * rateOfInterest * tenureInYear);
//	        ledger.setPayableAmountwithInterest(payableAmountWithInterest);
//
//	        // Set tenure and monthly EMI
//	        ledger.setTenure(tenureInYear);
//	        ledger.setMonthlyEMI(customer.getSl().getMonthlyEmiAmount());
//
//	        // Calculate total amount paid till date
//	        double amountPaidTillDate = customer.getLedger().stream()
//	                .mapToDouble(Ledger::getAmountPaidtillDate)
//	                .sum();
//	        amountPaidTillDate += payment;
//	        ledger.setAmountPaidtillDate(amountPaidTillDate);
//
//	        // Calculate remaining amount
//	        Double remainingAmount = payableAmountWithInterest - amountPaidTillDate;
//	        ledger.setRemainingAmount(remainingAmount);
//
//	        // Calculate next EMI start and end dates
//	        LocalDate lastEmiDate = customer.getLedger().isEmpty()
//	                ? LocalDate.now()
//	                : LocalDate.parse(customer.getLedger().get(customer.getLedger().size() - 1).getNextEmiDateEnd());
//	        LocalDate nextEmiStartDate = lastEmiDate.plusDays(1);
//	        LocalDate nextEmiEndDate = nextEmiStartDate.plusMonths(1);
//	        ledger.setNextEmiDatestart(nextEmiStartDate.toString());
//	        ledger.setNextEmiDateEnd(nextEmiEndDate.toString());
//
//	        // Calculate defaulter count
//	        int defaulterCount = customer.getLedger().stream()
//	                .mapToInt(Ledger::getDefaulterCount)
//	                .max()
//	                .orElse(0);
//	        String currentEmiStatus = payment == 0 ? "Missed" : "Paid";
//	        if (currentEmiStatus.equals("Missed")) {
//	            defaulterCount++;
//	        }
//	        ledger.setDefaulterCount(defaulterCount);
//
//	        // Set previous and current EMI status
//	        String previousEmiStatus = customer.getLedger().isEmpty()
//	                ? "N/A"
//	                : customer.getLedger().get(customer.getLedger().size() - 1).getCurrentMonthEmiStatus();
//	        ledger.setPreviousEmitStatus(previousEmiStatus);
//	        ledger.setCurrentMonthEmiStatus(currentEmiStatus);
//
//	        // Calculate loan end date
//	        LocalDate firstEmiDate = customer.getLedger().isEmpty()
//	                ? LocalDate.now()
//	                : LocalDate.parse((CharSequence) customer.getLedger().get(0).getLedgerCreatedDate());
//	        LocalDate loanEndDate = firstEmiDate.plusYears(tenureInYear);
//	        ledger.setLoanEndDate(loanEndDate.toString());
//
//	        // Set loan status
//	        String loanStatus = remainingAmount > 0 ? "Ongoing" : "Completed";
//	        ledger.setLoanStatus(loanStatus);
//
//	        // Add ledger to customer's ledger list
//	        customer.getLedger().add(ledger);
//
//	        // Save the updated Customer entity
//	        return lr.save(customer);
//	    } else {
//	        throw new RuntimeException("Customer with ID " + customerId + " not found.");
//	    }
//	}
	public loanServiceImpl(LoanRepo lr) {
		this.lr = lr;
	}

	@Override
	public Customer createleager(int customerId, Double payment) throws Exception {

		Optional<Customer> byId = lr.findById(customerId);
		if (!byId.isPresent()) {
			throw new Exception("Customer with ID " + customerId + " not found.");
		}

		Customer customer = byId.get();

		// Initialize ledger list if null
		if (customer.getLedger() == null) {
			customer.setLedger(new ArrayList<>());
		}

		// Fetch loan details
		Double principal = customer.getSl().getLoanAmtSanctioned();
		Double rateOfInterest = (double) customer.getSl().getRateOfInterest();
		Integer tenureInYear = customer.getSl().getLoanTenureInYear();

		if (principal == null || rateOfInterest == null || tenureInYear == null) {
			throw new RuntimeException("Required loan details are missing for Customer ID: " + customerId);
		}

		int totalMonths = tenureInYear * 12;
		double monthlyInterestRate = rateOfInterest / 12 / 100;

		// EMI Calculation (Reducing Balance Method)
		double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalMonths))
				/ (Math.pow(1 + monthlyInterestRate, totalMonths) - 1);
		Double payableAmountWithInterest = emi * totalMonths; // Total payable amount

		// Create new Ledger entry
		Ledger ledger = new Ledger();
		ledger.setLedgerCreatedDate(LocalDate.now().toString());
		ledger.setTotalLoanAmount(customer.getLoanDisbursement().getTotalAmount());
		ledger.setPayableAmountwithInterest(payableAmountWithInterest);
		ledger.setTenure(tenureInYear);
		ledger.setMonthlyEMI(emi);

		// Calculate total amount paid till date
		double amountPaidTillDate = customer.getLedger().stream().mapToDouble(Ledger::getAmountPaidtillDate).sum()
				+ payment;
		ledger.setAmountPaidtillDate(amountPaidTillDate);

		// Calculate remaining amount
//		Double remainingAmount = payableAmountWithInterest - amountPaidTillDate;
		Double remainingAmount = Math.max(0, payableAmountWithInterest - amountPaidTillDate);

		ledger.setRemainingAmount(remainingAmount);

		// Set EMI Dates
		LocalDate lastEmiDate = customer.getLedger().isEmpty() ? LocalDate.now()
				: LocalDate.parse(customer.getLedger().get(customer.getLedger().size() - 1).getLedgerCreatedDate());
		LocalDate nextEmiStartDate = lastEmiDate.plusMonths(1);
		LocalDate nextEmiEndDate = nextEmiStartDate.plusMonths(1);
		ledger.setNextEmiDatestart(nextEmiStartDate.toString());
		ledger.setNextEmiDateEnd(nextEmiEndDate.toString());

		// Defaulter Count Tracking
		long missedEmis = customer.getLedger().stream().filter(l -> "Missed".equals(l.getCurrentMonthEmiStatus()))
				.count();
		int defaulterCount = (int) missedEmis;
		if (payment == 0) {
			defaulterCount++;
		}
		ledger.setDefaulterCount(defaulterCount);

		// Set EMI status
		String previousEmiStatus = customer.getLedger().isEmpty() ? "N/A"
				: customer.getLedger().get(customer.getLedger().size() - 1).getCurrentMonthEmiStatus();
		ledger.setPreviousEmitStatus(previousEmiStatus);
		ledger.setCurrentMonthEmiStatus(payment == 0 ? "Missed" : "Paid");

		// Loan end date calculation
		LocalDate firstEmiDate = customer.getLedger().isEmpty() ? LocalDate.now()
				: LocalDate.parse(customer.getLedger().get(0).getLedgerCreatedDate());
		LocalDate loanEndDate = firstEmiDate.plusYears(tenureInYear);
		ledger.setLoanEndDate(loanEndDate.toString());

		// Set loan status
		ledger.setLoanStatus(remainingAmount > 0 ? "Ongoing" : "Completed");

		// Add ledger entry
		customer.getLedger().add(ledger);

		// Save customer with updated ledger
		return lr.save(customer);

	}

}
