package in.cm.service;

import java.util.Optional;


import in.cm.model.Customer;
public interface CMserviceI {
 
	public Customer updateSanction(int customerId, Customer cs);

	
	public Optional<Customer> findById(Integer customerId);

	
}
