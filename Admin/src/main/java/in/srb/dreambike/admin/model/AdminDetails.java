package in.srb.dreambike.admin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class AdminDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int empId;
	private String empFirstName;
	private String empMiddleName;
	private String empLastName;
	private String empEmail;
	private float empSalary;
	private int empAge;
	
	private String username;
	private String passwoed;
	@Lob
	@Column(length=999999999)
	private byte[] empImage;
	@Lob
	@Column(length=999999999)
	private byte[] empPancard;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 999999999)
	private UserType usertype;
	 
	
}
