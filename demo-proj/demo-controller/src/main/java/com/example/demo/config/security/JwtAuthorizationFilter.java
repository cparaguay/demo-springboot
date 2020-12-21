package com.example.demo.config.security;

import static com.example.demo.dominio.tipo.TipoValorConfiguracion.JWT_PARAMETER;
import static com.example.demo.dominio.tipo.TipoValorConfiguracion.JWT_PARAMETER_AUTHORITIES;
import static com.example.demo.dominio.tipo.TipoValorConfiguracion.JWT_PARAMETER_HEADER;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.controller.path.SecurityControllerPath;
import com.example.demo.service.IValorConfiguracionService;
import com.example.demo.service.impl.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;

/**
 * Clase que filtra la peticiones, la intercepta y ejecuta la validacion jwt implementada.
 * 
 * @author dalozz
 *
 */
@Component
@Log4j2
public class JwtAuthorizationFilter extends OncePerRequestFilter  {
	
	@Autowired
	private IValorConfiguracionService iValorConfiguracionService;
	
	@Autowired
	private JwtService jwtService;
	
	/**
	 * Intercepta las peticiones antes de llegar a los controladores.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String keyHeader = iValorConfiguracionService.obtenerValor(JWT_PARAMETER.getKey(), JWT_PARAMETER_HEADER.getKey()).getValor();

		boolean existsJwt = jwtService.hasJwt(request.getHeader(keyHeader));
		
		if (existsJwt) {
			
			try {
				Claims claims = jwtService.validateJwt(request.getHeader(keyHeader));
				
				String keyAuthorities = iValorConfiguracionService.obtenerValor(JWT_PARAMETER.getKey(), JWT_PARAMETER_AUTHORITIES.getKey()).getValor();
				
				if (claims.get(keyAuthorities) != null) {
					setUpSpringAuthentication(claims, keyAuthorities);
				} else {
					SecurityContextHolder.clearContext();
				}
			}catch(ExpiredJwtException exception) {
				
				String urlRefreshToken = StringUtils.join(request.getContextPath(), SecurityControllerPath.RESOURCE_PATH, SecurityControllerPath.REFRESH_TOKEN.PATH);
				if(BooleanUtils.isFalse(StringUtils.equals(request.getRequestURI(), urlRefreshToken))) {
					log.error(exception.getMessage(), exception);
					throw exception;	
				}
			}catch(Exception exception) {
				log.error(exception.getMessage(), exception);
				throw exception;
			}
		}
		
		//Permite seguir el camino de la peticion
		filterChain.doFilter(request, response);
	}
	
	/**
	 * Metodo para autenticarnos dentro del flujo de Spring, ademas
	 * guardamos dentro del contexto 
	 * 
	 * @param claims
	 * @param keyAuthorities 
	 */
	private void setUpSpringAuthentication(Claims claims, String keyAuthorities) {
				
		List<String> authorities = Arrays.asList(claims.get(keyAuthorities).toString());

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				claims.getSubject(), 
				null, 
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
				);
		//Guardamos en el contexto de la request de spring
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

}
