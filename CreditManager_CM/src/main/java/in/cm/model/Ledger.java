package in.cm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Ledger {
	@Id
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

    // Getters and Setters
}

