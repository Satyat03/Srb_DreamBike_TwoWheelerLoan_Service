package in.srb.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.srb.model.Customer;

@Repository
public interface LoanRepo extends JpaRepository<Customer, Integer> {
	
	public List<Customer> findAllByLoanStatus(String loanStatus);

	public Customer findByUsernameAndPassword(String username, String password);

	public java.util.Optional<Customer> findByUsername(String username);

	

}
