package com.employeeServices.openFeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.employeeServices.responses.AddressResponse;

@FeignClient(name = "address-services", path="/api")
public interface AddressClient {
	
	@GetMapping("/address/{employeeId}")
	ResponseEntity<AddressResponse> getAddressById(@PathVariable("employeeId") int id);
	
	@PostMapping("/address/add")
	ResponseEntity<String> addAddress(@RequestBody AddressResponse addressResponse);

}
