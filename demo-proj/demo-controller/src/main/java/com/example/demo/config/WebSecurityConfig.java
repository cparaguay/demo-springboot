package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.config.security.JwtAuthorizationFilter;
import com.example.demo.config.utils.Profiles;
import com.example.demo.controller.path.ExceptionControllerPath;
import com.example.demo.controller.path.H2ControllerPath;
import com.example.demo.controller.path.OpenApiControllerPath;
import com.example.demo.controller.path.SecurityControllerPath;

/**
 *  Esta clase decorada con @EnableWebSecurity y @Configuration nos permite especificar la 
 *  configuración de acceso a los recursos publicados.
 *  En este caso se permiten todas las llamadas al controlador /security/login, pero el resto de las llamadas requieren autenticación.
 * @author dalozz
 *
 */
@EnableWebSecurity
@Configuration
@ComponentScan("com.example.demo.config.security")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
    @Value("${spring.profiles.active}")
    private String profile;
    	
	@Autowired
	private JwtAuthorizationFilter jwtAuthorizationFilter;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Metodo de configuracion de accesos a la url.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

		if(profile.equals(Profiles.OPEN_API)) {
			
			http.authorizeRequests().antMatchers(HttpMethod.POST, SecurityControllerPath.LOGIN.FULL_PATH).permitAll();
			
			http.authorizeRequests().antMatchers(H2ControllerPath.RESOURCE).permitAll();
			
			http.authorizeRequests().antMatchers(HttpMethod.GET, OpenApiControllerPath.RESOURCE, OpenApiControllerPath.RESOURCE_UI, OpenApiControllerPath.RESOURSE_OWN, OpenApiControllerPath.RESOURCE_DOC).permitAll();
			
			http.authorizeRequests().anyRequest().authenticated();
			return;
		}
		
		http.authorizeRequests().antMatchers(HttpMethod.POST, SecurityControllerPath.LOGIN.FULL_PATH).permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, SecurityControllerPath.REFRESH_TOKEN.FULL_PATH).permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,  ExceptionControllerPath.ALL_RESOURCE).permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable().cors().disable();
	}
}
