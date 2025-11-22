package com.alocode.api_gateway.bean;

import com.alocode.api_gateway.filter.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayBean {

	@Bean
	public AuthFilter authFilter() {
		return new AuthFilter();
	}
}
