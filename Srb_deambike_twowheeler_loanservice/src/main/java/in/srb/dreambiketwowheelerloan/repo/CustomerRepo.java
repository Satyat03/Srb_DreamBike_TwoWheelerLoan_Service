package in.srb.dreambiketwowheelerloan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.srb.dreambiketwowheelerloan.model.CustomerEnquiry;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEnquiry, Integer> {

}
