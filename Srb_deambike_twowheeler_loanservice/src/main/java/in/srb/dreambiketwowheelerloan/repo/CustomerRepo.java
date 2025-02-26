package in.srb.dreambiketwowheelerloan.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.srb.dreambiketwowheelerloan.model.Cibil;
import in.srb.dreambiketwowheelerloan.model.CustomerEnquiry;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEnquiry, Integer> {

//	@Query("SELECT ci.status FROM CustomerEnquiry where ci.status= :status")
//	List<String> findAllByStatus(@Param("status") String status);

	 // Custom query to find enquiries with status 'Pending'
    @Query("SELECT ce FROM CustomerEnquiry ce WHERE ce.enquiryStatus = 'Pending'")
    public List<CustomerEnquiry> findPendingEnquiries();

    @Query("SELECT ce FROM CustomerEnquiry ce WHERE ce.enquiryStatus = 'Approved'")
    public List<CustomerEnquiry> findApproved();
	
    @Query("SELECT ce FROM CustomerEnquiry ce WHERE ce.enquiryStatus = 'Rejected'")
    public List<CustomerEnquiry> findRejected();
	
}

