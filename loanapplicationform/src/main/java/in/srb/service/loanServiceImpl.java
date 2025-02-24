package in.srb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.srb.model.Customer;
import in.srb.repo.LoanRepo;
import jakarta.transaction.Transactional;
@Service
public class loanServiceImpl implements LoanServiceI {

	@Autowired
	LoanRepo lr;
	
	@Transactional
	@Override
	public Customer saveData(Customer c) {
		Customer customer = lr.save(c);
		System.out.println(customer.toString());
		return customer;
	}

}
