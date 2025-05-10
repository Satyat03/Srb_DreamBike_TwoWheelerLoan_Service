package in.cm.service;

import java.util.Optional;


import in.cm.model.Customer;
public interface CMserviceI {
 
	public Customer updateSanction(int customerId, int tenure);

	
	public Optional<Customer> findById(Integer customerId);

	public void saveData(Customer c);
}
