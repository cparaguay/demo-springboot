package com.example.demo.service.impl;

import static com.example.demo.dominio.tipo.TipoValorConfiguracion.JWT_PARAMETER;
import static com.example.demo.dominio.tipo.TipoValorConfiguracion.JWT_PARAMETER_PREFIXTOKEN;
import static com.example.demo.dominio.tipo.TipoValorConfiguracion.JWT_PARAMETER_SECRET;
import static com.example.demo.dominio.tipo.TipoValorConfiguracion.JWT_PARAMETER_AUTHORITIES;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.service.IValorConfiguracionService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Clase utilitaria para la generacion del jwt
 * 
 * @author dalozz
 *
 */
@Component
public class JwtService {
	
	@Autowired
	private IValorConfiguracionService iValorConfiguracionService;
	
	/**
	 * Genera el jwt, recibe como argumento el id del usuario.
	 * @param username
	 * @return
	 */
	public String doGenerateToken(UserDetails user) {
		String secretKey = iValorConfiguracionService.obtenerValor(JWT_PARAMETER.getKey(), JWT_PARAMETER_SECRET.getKey()).getValor();
		String prefixToken = iValorConfiguracionService.obtenerValor(JWT_PARAMETER.getKey(), JWT_PARAMETER_PREFIXTOKEN.getKey()).getValor();
		String keyAuthirities = iValorConfiguracionService.obtenerValor(JWT_PARAMETER.getKey(), JWT_PARAMETER_AUTHORITIES.getKey()).getValor();
		Collection<? extends GrantedAuthority> grantedAuthorities = user.getAuthorities();
		
		String token = Jwts
				.builder()
				.setId(UUID.randomUUID().toString())
				.setSubject(user.getUsername())
				.claim(keyAuthirities,
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();
		
		return StringUtils.join(prefixToken, StringUtils.SPACE, token);
	}
	
	public Claims validateJwt(String authToken) {
		
		String keyPrefixToken = iValorConfiguracionService.obtenerValor(JWT_PARAMETER.getKey(), JWT_PARAMETER_PREFIXTOKEN.getKey()).getValor();
		String keySecret = iValorConfiguracionService.obtenerValor(JWT_PARAMETER.getKey(), JWT_PARAMETER_SECRET.getKey()).getValor();		
		String jwtToken = authToken.replace(keyPrefixToken, StringUtils.EMPTY);
		
		return Jwts.parser().setSigningKey(keySecret.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	/**
	 * Validando la existencia de la cabezera Bearer
	 * @param request
	 * @param res
	 * @return Boolean según la existencia de la cabecera.
	 */
	public boolean hasJwt(String header) {
		String keyPrefixToken = iValorConfiguracionService.obtenerValor(JWT_PARAMETER.getKey(), JWT_PARAMETER_PREFIXTOKEN.getKey()).getValor();
		String authenticationHeader = header;
		if (authenticationHeader == null || !authenticationHeader.startsWith(keyPrefixToken))
			return Boolean.FALSE;
		
		return Boolean.TRUE;
	}
	
}
