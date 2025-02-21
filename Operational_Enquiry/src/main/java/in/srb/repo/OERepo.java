package in.srb.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.srb.model.CustomerEnquiry;

public interface OERepo extends JpaRepository<CustomerEnquiry, Integer>
{
	
}

