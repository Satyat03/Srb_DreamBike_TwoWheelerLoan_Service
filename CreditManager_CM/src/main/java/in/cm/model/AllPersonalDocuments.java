package in.cm.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AllPersonalDocuments {
	
	@Id
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
    private byte[] salarySlips;
    
    


    
    
}
