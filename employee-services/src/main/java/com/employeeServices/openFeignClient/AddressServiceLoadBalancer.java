package com.employeeServices.openFeignClient;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

import feign.Feign;

//@LoadBalancerClient(name = "address-services", configuration = MyCustomLoadBalancer.class)
@LoadBalancerClient(name = "address-services")
public class AddressServiceLoadBalancer {
	
	@LoadBalanced
	@Bean
	public Feign.Builder feignBuilder(){
		return Feign.builder();
	}
}
