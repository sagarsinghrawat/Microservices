package com.apiGateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticateFilter extends AbstractGatewayFilterFactory<AuthenticateFilter.Config> {
	
	
	@Autowired
	private RouteValidator routeValidator;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public AuthenticateFilter() {
		super(Config.class);
		// TODO Auto-generated constructor stub
	}

	public static class Config{
		
	}

	@Override
	public GatewayFilter apply(Config config) {
		// TODO Auto-generated method stub
		return ((exchange, chain) -> {
			
			if(routeValidator.isSecured.test(exchange.getRequest())) {
				//Route contains header or not
				
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("User Not Valid");
				}
				
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if(authHeader!=null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}else {
					throw new RuntimeException("token not pass");
				}
				
				try {
					System.out.println(authHeader);
					//Rest Call to JwtService
					//restTemplate.getForObject("http://JWTSecurity/auth/validate?token={authHeader}", String.class,authHeader);
					restTemplate.getForObject("http://localhost:9090/auth/validate?token="+authHeader,String.class);
				}catch(Exception ex) {
					throw new RuntimeException("Invalid User");
				}
			}
			return chain.filter(exchange);
		});
	}
}
