package in.srb.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

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






import java.io.ByteArrayOutputStream;


@Service
public class loanServiceImpl implements LoanServiceI {

	@Autowired
	LoanRepo lr;
	@Autowired  // Inject EmailServiceImpl properly
	EmailServiceImpl emailService;


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
    if (customer.getLedger() == null) {
        customer.setLedger(new ArrayList<>());
    }

    // Loan Details
    Double principal = customer.getSl().getLoanAmtSanctioned();
    Double rateOfInterest = (double) customer.getSl().getRateOfInterest();
    Integer tenureInYear = customer.getSl().getLoanTenureInYear();

    if (principal == null || rateOfInterest == null || tenureInYear == null) {
        throw new RuntimeException("Required loan details are missing for Customer ID: " + customerId);
    }

    int totalMonths = tenureInYear * 12;
    double monthlyInterestRate = rateOfInterest / 12 / 100;
    double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalMonths))
            / (Math.pow(1 + monthlyInterestRate, totalMonths) - 1);
    Double payableAmountWithInterest = emi * totalMonths;

    // Create Ledger Entry
    Ledger ledger = new Ledger();
    ledger.setLedgerCreatedDate(LocalDate.now().toString());
    ledger.setTotalLoanAmount(customer.getLoanDisbursement().getTotalAmount());
    ledger.setPayableAmountwithInterest(payableAmountWithInterest);
    ledger.setTenure(tenureInYear);
    ledger.setMonthlyEMI(emi);

    double lastAmountPaid = customer.getLedger().isEmpty() ? 0
            : customer.getLedger().get(customer.getLedger().size() - 1).getAmountPaidtillDate();

    double amountPaidTillDate = lastAmountPaid + payment;
    ledger.setAmountPaidtillDate(amountPaidTillDate);

    Double remainingAmount = Math.max(0, payableAmountWithInterest - amountPaidTillDate);
    ledger.setRemainingAmount(remainingAmount);

    LocalDate lastEmiDate = customer.getLedger().isEmpty() ? LocalDate.now()
            : LocalDate.parse(customer.getLedger().get(customer.getLedger().size() - 1).getLedgerCreatedDate());
    LocalDate nextEmiStartDate = lastEmiDate.plusMonths(1);
    LocalDate nextEmiEndDate = nextEmiStartDate.plusMonths(1);
    ledger.setNextEmiDatestart(nextEmiStartDate.toString());
    ledger.setNextEmiDateEnd(nextEmiEndDate.toString());

    long missedEmis = customer.getLedger().stream().filter(l -> "Missed".equals(l.getCurrentMonthEmiStatus()))
            .count();
    int defaulterCount = (int) missedEmis;
    if (payment == 0) {
        defaulterCount++;
    }
    ledger.setDefaulterCount(defaulterCount);

    String previousEmiStatus = customer.getLedger().isEmpty() ? "N/A"
            : customer.getLedger().get(customer.getLedger().size() - 1).getCurrentMonthEmiStatus();
    ledger.setPreviousEmitStatus(previousEmiStatus);
    ledger.setCurrentMonthEmiStatus(payment == 0 ? "Missed" : "Paid");

    LocalDate firstEmiDate = customer.getLedger().isEmpty() ? LocalDate.now()
            : LocalDate.parse(customer.getLedger().get(0).getLedgerCreatedDate());
    LocalDate loanEndDate = firstEmiDate.plusYears(tenureInYear);
    ledger.setLoanEndDate(loanEndDate.toString());

    ledger.setLoanStatus(remainingAmount > 0 ? "Ongoing" : "Completed");

    // Generate PDF
    byte[] pdfBytes = generateLedgerPdf(ledger);
    ledger.setLedgerPdf(pdfBytes);
    
    // Send email
    String customerEmail = customer.getCustomerEmail();  // Ensure the Customer entity has an email field
    String subject = "Loan Ledger Details";
    String body = "Dear " + customer.getCustomerName() + ",\n\nPlease find your loan ledger details attached as a PDF.\n\nBest regards,\nSrb Insurance";
    emailService.sendEmailWithAttachment(customerEmail, subject, body, pdfBytes);


    customer.getLedger().add(ledger);

    return lr.save(customer);
}


private byte[] generateLedgerPdf(Ledger ledger) 
{
    try {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Adding Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("Ledger Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        // Adding Ledger Details
        Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        document.add(new Paragraph("Ledger Created Date: " + ledger.getLedgerCreatedDate(), textFont));
        document.add(new Paragraph("Total Loan Amount: " + ledger.getTotalLoanAmount(), textFont));
        document.add(new Paragraph("Payable Amount (with Interest): " + ledger.getPayableAmountwithInterest(), textFont));
        document.add(new Paragraph("Tenure (Years): " + ledger.getTenure(), textFont));
        document.add(new Paragraph("Monthly EMI: " + ledger.getMonthlyEMI(), textFont));
        document.add(new Paragraph("Amount Paid Till Date: " + ledger.getAmountPaidtillDate(), textFont));
        document.add(new Paragraph("Remaining Amount: " + ledger.getRemainingAmount(), textFont));
        document.add(new Paragraph("Next EMI Start Date: " + ledger.getNextEmiDatestart(), textFont));
        document.add(new Paragraph("Next EMI End Date: " + ledger.getNextEmiDateEnd(), textFont));
        document.add(new Paragraph("Loan End Date: " + ledger.getLoanEndDate(), textFont));
        document.add(new Paragraph("Loan Status: " + ledger.getLoanStatus(), textFont));
        document.add(new Paragraph("Current Month EMI Status: " + ledger.getCurrentMonthEmiStatus(), textFont));

        document.close();
        return outputStream.toByteArray();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }



/**	@Override
	public Customer createleager(int customerId, Double payment) throws Exception {

		// TODO Auto-generated method stub
		return null;
	}


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
		double lastAmountPaid = customer.getLedger().isEmpty() ? 0
				: customer.getLedger().get(customer.getLedger().size() - 1).getAmountPaidtillDate();

		double amountPaidTillDate = lastAmountPaid + payment;
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

	}**/



	
 
	    
	




}
}
