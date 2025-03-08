package in.srb.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class Ledger {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer ledgerId;
    private String ledgerCreatedDate;
    private Double totalLoanAmount;
    private Double payableAmountwithInterest;
    private Integer tenure;
    private Double monthlyEMI;
    private Double amountPaidtillDate;
    private Double remainingAmount;
    private String nextEmiDatestart;
    private String nextEmiDateEnd;
    private Integer defaulterCount;
    private String previousEmitStatus;
    private String currentMonthEmiStatus;
    private String loanEndDate;
    private String loanStatus;
    
    @Lob
    @Column(length = 999999999)
    private byte[] ledgerPdf;

    // Getters and Setters
}

