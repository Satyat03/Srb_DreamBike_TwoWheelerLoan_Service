package in.srb.dreambike.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.srb.dreambike.admin.model.AdminDetails;

@Repository
public interface AdminRepo extends JpaRepository<AdminDetails, Integer> {

	
	public AdminDetails findByUsernameAndPassword(String username,String password);

}
