package com.addressServices.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.addressServices.addressRepo.AddressRepo;
import com.addressServices.entities.Address;
import com.addressServices.responses.AddressResponses;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public void addAddress(Address address) {
		addressRepo.save(address);
	}
	
	public AddressResponses getAddressById(int employeeId){
		Address address =  addressRepo.findAddressByEmployeeId(employeeId);
		System.out.println(address);
		AddressResponses addressResponses = modelMapper.map(address, AddressResponses.class);
		return addressResponses;
	}
	
	public List<Address> getAllAddresses(){
		List<Address> addresses = addressRepo.findAll();
		return addresses;
	}
	
	
}
