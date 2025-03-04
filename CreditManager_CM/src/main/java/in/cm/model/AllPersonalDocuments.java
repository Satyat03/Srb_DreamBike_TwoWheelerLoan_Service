package in.cm.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class AllPersonalDocuments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int documentID;

	@Column(length=999999)
    private byte[] addressProof;
	@Column(length=999999)
    private byte[] panCard;
	@Column(length=999999)
    private byte[] incomeTax;
	@Column(length=999999)
    private byte[] addharCard;
	@Column(length=999999)
    private byte[] photo;
	@Column(length=999999)
    private byte[] signature;
	@Column(length=999999)
    private byte[] bankCheque;
	@Column(length=999999)

	@Lob
	@Column(length=999999999)
    private byte[] addressProof;
	@Lob
	@Column(length=999999999)
    private byte[] panCard;
	@Lob
	@Column(length=999999999)
    private byte[] incomeTax;
	@Lob
	@Column(length=999999999)
    private byte[] addharCard;
	@Lob
	@Column(length=999999999)
    private byte[] photo;
	@Lob
	@Column(length=999999999)
    private byte[] signature;
	@Lob
	@Column(length=999999999)
    private byte[] bankCheque;
	@Lob
	@Column(length=999999999)

    private byte[] salarySlips;   
}
