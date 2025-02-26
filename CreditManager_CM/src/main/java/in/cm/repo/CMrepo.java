package in.cm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cm.model.Customer;

@Repository
public interface CMrepo extends JpaRepository<Customer, Integer> {

}
