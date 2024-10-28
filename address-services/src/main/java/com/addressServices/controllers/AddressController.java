package com.addressServices.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addressServices.entities.Address;
import com.addressServices.responses.AddressResponses;
import com.addressServices.services.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping("")
	ResponseEntity<List<Address>> getAllAddress(){
		List<Address> address = addressService.getAllAddresses();
		return ResponseEntity.status(HttpStatus.OK).body(address);
	}
	@GetMapping("/{employeeId}")
	ResponseEntity<AddressResponses> getAddressById(@PathVariable("employeeId") int id) {
		AddressResponses addressResponses = addressService.getAddressById(id);  
		return ResponseEntity.status(HttpStatus.OK).body(addressResponses);
	}
	
	@PostMapping("/add")
	ResponseEntity<String> addAddress(@RequestBody AddressResponses addressResponses){
		Address address = new Address();
		
		System.out.println(addressResponses.getEmp_id());
		System.out.println(addressResponses.getLane1());
		System.out.println(addressResponses.getLane2());
		address.setEmp_id(addressResponses.getEmp_id());
		address.setLane1(addressResponses.getLane1());
		address.setLane2(addressResponses.getLane2());
		address.setState(addressResponses.getState());
		address.setZip_code(addressResponses.getZip_code());
		
		addressService.addAddress(address);
		return ResponseEntity.status(HttpStatus.CREATED).body("SuccessFully Created");
	}
	
	
}
