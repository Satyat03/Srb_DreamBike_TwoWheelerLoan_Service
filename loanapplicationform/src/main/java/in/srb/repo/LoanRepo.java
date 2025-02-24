package in.srb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.srb.model.Customer;

@Repository
public interface LoanRepo extends JpaRepository<Customer, Integer> {

}
