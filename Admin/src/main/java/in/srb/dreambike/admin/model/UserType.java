package in.srb.dreambike.admin.model;

public enum UserType 
{

	CRM("Customer Relationship Maneger"),
	OE("Operational Executive"),
	CM("Credit Maneger"),
	AH("Account Head"),
	ADMIN("Administrator");
	
	private final String discription;

	UserType(String discription) 
	{
		this.discription=discription;
	}
	
	public String getDiscription()
	{
		return discription;
	}
	
}
