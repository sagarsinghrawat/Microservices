package com.employeeServices.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.employeeServices.entities.Employee;
import com.employeeServices.repo.EmployeeRepo;
import com.employeeServices.responses.EmployeeResponse;
import com.employeeServices.service.EmployeeService;

import feign.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
//	private EmployeeRepo employeeRepo;
	private EmployeeService employeeService;
	
	@GetMapping("")
	@RateLimiter(name = "addressEmployeeRateLimiter", fallbackMethod = "callBackForGetEmployee")
	public ResponseEntity< List<Employee>> getAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployee();
		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}
	
	ResponseEntity< List<Employee> > callBackForGetEmployee(Exception ex){
		List<Employee> employees = new ArrayList<Employee>(); 
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(employees);
	}
	
	
	@GetMapping("/{id}")
//	@CircuitBreaker(name = "addressEmployeeBreaker", fallbackMethod = "addressFallBack")
	@Retry(name = "addressEmployeeRetry", fallbackMethod = "addressFallBack")
	@RateLimiter(name = "addressEmployeeRateLimiter", fallbackMethod = "addressFallBack")
	ResponseEntity<EmployeeResponse> getEmployeeDetails(@PathVariable("id") int id) {
		EmployeeResponse employeeResponse =  employeeService.getEmployeeById(id);
		return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
	}
	
	ResponseEntity<EmployeeResponse> addressFallBack(int id, Exception ex){
		EmployeeResponse employeeResponse = new EmployeeResponse();
		return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);  
	}
	
	@PostMapping("/add")
	@Transactional
	//@CircuitBreaker(name = "addressEmployeeBreaker", fallbackMethod = "callBackForAddEmployee")
	@Retry(name = "addressEmployeeBreaker", fallbackMethod = "callBackForAddEmployee")
	@RateLimiter(name = "addressEmployeeRateLimiter", fallbackMethod = "callBackForAddEmployee")
	ResponseEntity<String> addEmployee(@RequestBody EmployeeResponse employeeResponse){
		employeeService.addEmployee(employeeResponse);
		return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
	}
	
	ResponseEntity<String> callBackForAddEmployee(EmployeeResponse employeeResponse, Exception ex){
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Services is down, try after some time");
	}
}
