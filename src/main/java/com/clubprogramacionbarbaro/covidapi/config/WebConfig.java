package com.clubprogramacionbarbaro.covidapi.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
	
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter("12345678"));
		registrationBean.addUrlPatterns("/v1/*");
		return registrationBean;
	}
}
