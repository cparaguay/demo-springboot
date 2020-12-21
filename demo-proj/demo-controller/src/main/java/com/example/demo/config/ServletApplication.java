package com.example.demo.config;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.config.utils.Profiles;
import com.example.demo.controller.path.OpenApiControllerPath;
import com.example.demo.controller.path.SecurityControllerPath;
import com.example.demo.interceptor.GenerateTrackingIdHttpRequestInterceptor;
import com.example.demo.interceptor.LoggerHttpRequestInterceptor;
import com.example.demo.interceptor.ValidateTrackingIdHttpRequestInterceptor;



@Configuration
@ComponentScan(basePackages = {"com.example.demo.controller"})
public class ServletApplication implements  WebMvcConfigurer  {

    @Value("${spring.profiles.active}")
    private String profile;
    
    
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerHttpRequestInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new GenerateTrackingIdHttpRequestInterceptor()).addPathPatterns(SecurityControllerPath.LOGIN.FULL_PATH);
				
		if(BooleanUtils.isFalse(profile.equals(Profiles.OPEN_API))) {
			registry.addInterceptor(new ValidateTrackingIdHttpRequestInterceptor()).excludePathPatterns(SecurityControllerPath.LOGIN.FULL_PATH, OpenApiControllerPath.RESOURCE_DOC);
		}
		
	}
	
	
}
