package com.addressServices.addressRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.addressServices.entities.Address;
import com.addressServices.responses.AddressResponses;

public interface AddressRepo extends JpaRepository<Address, Integer> {
	
//	@Query(nativeQuery = true, value = "select  a.id, a.lane1, a.lane2, a.state, a.zip_code from address a\r\n"
//			+ "join employee e\r\n"
//			+ "on e.id = a.emp_id\r\n"
//			+ "where a.emp_id =:employeeId")
	
	@Query(nativeQuery = true, value = "select a.emp_id, a.id, a.lane1, a.lane2, a.state, a.zip_code from address a\r\n"
			+ "join employee e\r\n"
			+ "on e.id = a.emp_id\r\n"
			+ "where a.emp_id = :employeeId")
	Address findAddressByEmployeeId(@Param("employeeId") int employeeId);
}
