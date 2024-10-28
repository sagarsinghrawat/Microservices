package com.employeeServices.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.employeeServices.entities.Employee;
import com.employeeServices.openFeignClient.AddressClient;
import com.employeeServices.repo.EmployeeRepo;
import com.employeeServices.responses.AddressResponse;
import com.employeeServices.responses.EmployeeResponse;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient webClient;
	
	@Autowired
	private AddressClient addressClient;
	
//	@Autowired
//	private DiscoveryClient discoveryClient;
//	
//	@Autowired
//	private LoadBalancerClient loadBalancerClient;
//	@Value("${addressservice.instance.name}")
//	private String addressServiceName;
	
//	@Value("${addressservice.base.url}")
//	private String addressBaseURL; 
	
//	public EmployeeService(@Value("${addressservice.base.url}") String addressBaseURL, RestTemplateBuilder builder) {
//		System.out.println("url  "+addressBaseURL);
//		this.restTemplate = builder.rootUri(addressBaseURL).build();
//	}
	
	public EmployeeResponse getEmployeeById(int id) {	
		Employee employee = employeeRepo.findById(id).get();
		EmployeeResponse employeeResponse = modelMapper.map(employee,EmployeeResponse.class);

		
		//Using Rest Template
		//AddressResponse addressResponse = restTemplate.getForObject("/address/{employeeId}",AddressResponse.class,id );
		//AddressResponse addressResponse = callRestTemplateToAddressService(id); //callWebClientToAddressService(id);
		
		
		//Using Feign Client
		//AddressResponse addressResponse = addressClient.getAddressById(id).getBody();
		ResponseEntity<AddressResponse> addressResponseEntity = addressClient.getAddressById(id);
		AddressResponse addressResponse = addressResponseEntity.getBody();
		employeeResponse.setAddressResponse(addressResponse);
		
		return employeeResponse;
	}
	
	
	public AddressResponse callRestTemplateToAddressService(int id) {
//		//No load balancing here
////		List<ServiceInstance> instances = discoveryClient.getInstances("address-services");
////		ServiceInstance serviceInstance = instances.get(0);
////		String uri = serviceInstance.getUri().toString();
//		
//		//Load Balancer
////		ServiceInstance instance = loadBalancerClient.choose("address-services");
////		String uri = instance.getUri().toString();
////		String contextPath = instance.getMetadata().get("contextPath") ;
////		System.out.println(uri+contextPath);
//		
//		
//		//Load balancer using annotation -> @LoadBalanced in ResTemplate Bean in java config file
		return restTemplate.getForObject("http://address-services/api/address/{employeeId}",AddressResponse.class,id);
	}
	
//	public AddressResponse callWebClientToAddressService(int id) {
//		return webClient
//				.get()
//				.uri("/address/"+id)
//				.retrieve()
//				.bodyToMono(AddressResponse.class)
//				.block();
//	}
	public List<Employee> getAllEmployee(){
		List<Employee> employees = employeeRepo.findAll();
		return employees;
	}
	
//	public void insertEmployee(Employee employee) {
//		employeeRepo.save(employee);
//	}
//	
	
	public ResponseEntity<String> callRestTemplateToAddEmployeeAddress(AddressResponse addressResponse) {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://address-services/api/address/add", addressResponse, String.class);
		return responseEntity;
	}
	public String addEmployee(EmployeeResponse employeeResponse) {
		
		System.out.println("-------------"+employeeResponse);
		AddressResponse addressResponse = employeeResponse.getAddressResponse();
		Employee employee = new Employee();
		System.out.print("--------------"+addressResponse);
		employee.setName(employeeResponse.getName().toString());
		employee.setEmail(employeeResponse.getEmail());
		employee.setBloodGroup(employeeResponse.getBloodGroup());
		
		Employee saveEmployee = employeeRepo.save(employee);
		addressResponse.setEmp_id(saveEmployee.getId());
		//ResponseEntity<String> responseEntity = addressClient.addAddress(addressResponse);
		ResponseEntity<String> responseEntity = callRestTemplateToAddEmployeeAddress(addressResponse);
		return responseEntity.getBody();
	}
}
