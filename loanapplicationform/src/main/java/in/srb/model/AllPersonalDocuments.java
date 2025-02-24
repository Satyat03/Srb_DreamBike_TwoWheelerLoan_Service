package in.srb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class AllPersonalDocuments {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int documentID;
	@Lob
    @NotNull(message = "Address proof cannot be null")
    @Size(min = 1, max = 999999999, message = "Address proof size must be between 1 byte and 10MB")
    private byte[] addressProof;
	@Lob
    @NotNull(message = "PAN card cannot be null")
    @Size(min = 1, max = 999999999, message = "PAN card size must be between 1 byte and 10MB")
    private byte[] panCard;
	@Lob
    @NotNull(message = "incomeTax cannot be null")
    @Size(min = 1, max = 999999999, message = "incomeTax size must be between 1 byte and 5MB")
    private byte[] incomeTax;
	@Lob
    @NotNull(message = "addharCard cannot be null")
    @Size(min = 1, max = 999999999, message = "addharCard size must be between 1 byte and 5MB")
    private byte[] addharCard;
	@Lob
    @NotNull(message = "Photo cannot be null")
    @Size(min = 1, max = 999999999, message = "Photo size must be between 1 byte and 5MB")
    private byte[] photo;
	@Lob
    @NotNull(message = "signature cannot be null")
    @Size(min = 1, max = 999999999, message = "signature size must be between 1 byte and 5MB")
    private byte[] signature;
	@Lob
    @NotNull(message = "bankCheque cannot be null")
    @Size(min = 1, max = 999999999, message = "bankCheque size must be between 1 byte and 5MB")
    private byte[] bankCheque;
	@Lob
    @NotNull(message = "salarySlips cannot be null")
    @Size(min = 1, max = 999999999, message = "salarySlips size must be between 1 byte and 5MB")
    private byte[] salarySlips;
}
