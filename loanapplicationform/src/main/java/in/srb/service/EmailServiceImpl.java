package in.srb.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;

import jakarta.mail.internet.MimeMessage;
@Service
public class EmailServiceImpl {
	
	    @Autowired
	    private JavaMailSender mailSender;

	    public void sendEmailWithAttachment(String toEmail, String subject, String body, byte[] pdfBytes) throws Exception {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

	        helper.setTo(toEmail);
	        helper.setSubject(subject);
	        helper.setText(body);
	        helper.setFrom("saurabhtonge18@gmail.com");

	        // Attach PDF
	        InputStreamSource pdfSource = new ByteArrayResource(pdfBytes);
	        helper.addAttachment("Ledger.pdf", pdfSource);

	        mailSender.send(message);
	        System.out.println("Email sent successfully with PDF attachment!");
	    }

}
