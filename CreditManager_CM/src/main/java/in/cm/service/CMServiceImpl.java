package in.cm.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.cm.model.Customer;
import in.cm.model.SanctionLetter;
import in.cm.repo.CMrepo;

@Service
public class CMServiceImpl implements CMserviceI{

	@Autowired
    CMrepo cmr;
	
	
	@Override
	public Customer updateSanction(int customerId, Customer cs) {
		

	      
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
	            
	            c.setUsername(c.getCustomerEmail());
	            Random random=new Random();
	            c.setPassword("User"+"@"+random.nextInt(100, 999));
	            
	            
                
	           
		
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

	            String title = "ABC Finance Ltd.";

	            Document document=new Document(PageSize.A4);
			
				String content1 = "\n\n Dear " + c.getCustomerName()
						+ ","
						+ "\nABC Finance Ltd. is Happy to informed you that your loan application has been approved. ";

				String content2 = "\n\nWe hope that you find the terms and conditions of this loan satisfactory "
						+ "and that it will help you meet your financial needs.\n\nIf you have any questions or need any assistance regarding your loan, "
						+ "please do not hesitate to contact us.\n\nWe wish you all the best and thank you for choosing us."
						+ "\n\nSincerely,\n\n" + "Vijay Chaudhari (Credit Manager)";

				ByteArrayOutputStream opt = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, opt);
				//PdfWriter.getInstance(document, opt);
				document.open();

				Image img = null;
				try {
					img = Image.getInstance("E:\\CJC_Ajinkya\\11 (Project) (HML)\\ajt_logo.png");
					img.scalePercent(50, 50);
					img.setAlignment(Element.ALIGN_RIGHT);
					document.add(img);

				} catch (BadElementException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				Font titlefont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25);
				Paragraph titlepara = new Paragraph
						(title, titlefont);
				titlepara.setAlignment(Element.ALIGN_CENTER);
				document.add(titlepara);

				Font titlefont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
				Paragraph paracontent1 = new Paragraph(content1, titlefont2);
				document.add(paracontent1);

				PdfPTable table = new PdfPTable(2);
				table.setWidthPercentage(100f);
				table.setWidths(new int[] { 2, 2 });
				table.setSpacingBefore(10);

				PdfPCell cell = new PdfPCell();
				cell.setBackgroundColor(CMYKColor.WHITE);
				cell.setPadding(5);

				Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
				font.setColor(5, 5, 161);

				Font font1 = FontFactory.getFont(FontFactory.HELVETICA);
				font.setColor(5, 5, 161);

				cell.setPhrase(new Phrase("Loan amount Sanctioned", font));
				table.addCell(cell);

				cell.setPhrase(new Phrase(String.valueOf("₹ " + c.getSl().getLoanAmtSanctioned()),
						font1));
				table.addCell(cell);

				cell.setPhrase(new Phrase("loan tenure", font));
				table.addCell(cell);

				cell.setPhrase(new Phrase(String.valueOf(c.getSl().getLoanTenureInYear()), font1));
				table.addCell(cell);

				cell.setPhrase(new Phrase("interest rate", font));
				table.addCell(cell);

				cell.setPhrase(
						new Phrase(String.valueOf(c.getSl().getRateOfInterest()) + " %", font1));
				table.addCell(cell);

				cell.setPhrase(new Phrase("Sanction letter generated Date", font));
				table.addCell(cell);

//				Date date = new Date();
//				String curdate = date.toString();
				c.getSl().setSanctionDate(new Date());
				cell.setPhrase(
						new Phrase(String.valueOf(c.getSl().getSanctionDate()), font1));
				table.addCell(cell);

				cell.setPhrase(new Phrase("Total loan Amount with Intrest", font));
				table.addCell(cell);

				document.add(table);

				Font titlefont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
				Paragraph paracontent2 = new Paragraph(content2, titlefont3);
				document.add(paracontent2);
				document.close();
				
				ByteArrayInputStream byt = new ByteArrayInputStream(opt.toByteArray());
				byte[] bytes = byt.readAllBytes();
				c.getSl().setSanctionLetter(bytes);
	            // Save updated customer details in the database
	            return cmr.save(c);
	            
	        } else {
	            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
	        }
	    }
	@Override
	public Optional<Customer> findById(Integer customerId) {
		Optional<Customer> byId = cmr.findById(customerId);
		return byId;
	}
	@Override
	public void saveData(Customer c) {
		cmr.save(c);
	}

	}



