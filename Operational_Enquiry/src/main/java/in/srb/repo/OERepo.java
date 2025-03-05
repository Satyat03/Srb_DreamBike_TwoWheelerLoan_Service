package in.srb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.srb.model.CustomerEnquiry;
@Repository
public interface OERepo extends JpaRepository<CustomerEnquiry, Integer>
{
	
}

