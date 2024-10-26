package com.employeeServices.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.employeeServices.entities.Employee;
import com.employeeServices.repo.EmployeeRepo;
import com.employeeServices.responses.EmployeeResponse;
import com.employeeServices.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
//	private EmployeeRepo employeeRepo;
	private EmployeeService employeeService;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployee();
		return employees;
	}
	
	@GetMapping("/employees/{id}")
	ResponseEntity<EmployeeResponse> getEmployeeDetails(@PathVariable("id") int id) {
		EmployeeResponse employeeResponse =  employeeService.getEmployeeById(id);
		return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
	}
	
//	@PostMapping("/employee/add")
//	ResponseEntity<String> addEmployee() {
//		Employee emp = new Employee();
//		emp.setName("suraj");
//		emp.setEmail("suraj@gmail.com");
//		emp.setBloodGroup("b -ve");
//		employeeService.insertEmployee(emp);
//		return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
//	}
	
	@PostMapping("/employees/add")
	ResponseEntity<String> addEmployee(@RequestBody EmployeeResponse employeeResponse){
		employeeService.addEmployee(employeeResponse);
		return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
	}
}
