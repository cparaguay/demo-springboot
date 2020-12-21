package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.dominio.RefreshTokenDom;
import com.example.demo.dominio.RoleDom;
import com.example.demo.dominio.UserDom;
import com.example.demo.dto.ResponseRsDTO;
import com.example.demo.dto.SecurityRqDTO;
import com.example.demo.dto.SecurityRsDTO;
import com.example.demo.dto.TokenCredentialRqDTO;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.error.ErrorCode;
import com.example.demo.service.impl.JwtService;
import com.example.demo.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class) // Inicializa los mocks
public class UsuarioServiceTest {
	
	@Mock
	private IUserRepository iUsuarioRepository;
	
	@Mock
	private JwtService jwtTokenService;
	
	@Mock
	private IRefreshTokenService iRefreshTokenService;
	
	@InjectMocks
	private IUserService iUsuarioService = new UserServiceImpl();
	
	@DisplayName("Login success")
	@Test
	void loginSuccess() throws Exception {


		//Given
		String trackingId = String.valueOf(System.currentTimeMillis());

		SecurityRqDTO securityRqDTO = new SecurityRqDTO();
		securityRqDTO.setAccessKeyId("XP92870");
		securityRqDTO.setSecretAccessKey("12345678");
		securityRqDTO.setTrackingId(trackingId);
		
		
		// Mock
		UserDom userDomMock = new UserDom();
		userDomMock.setSecretAccessKey(RandomStringUtils.randomAlphanumeric(4));
		userDomMock.setAccessKeyId(RandomStringUtils.randomAlphanumeric(4));
		userDomMock.setLocked(Boolean.FALSE);
		userDomMock.setRoleDom(new RoleDom());
		userDomMock.getRoleDom().setValue("ROLE_ADMIN");
		
		UserDetails userToken = new User(userDomMock.getAccessKeyId(), userDomMock.getSecretAccessKey(), Arrays.asList(new SimpleGrantedAuthority(userDomMock.getRoleDom().getValue())));
		
		String jwtMock = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiWFA5Mjg3MCIsIkF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE1ODkwNzkyMTMsImV4cCI6MTU4OTA3OTgxM30.L13Laz8b89rpnWDlzR75pkl9q6VPhq828sX6nybbHKo8n8ITjvJCVTtOnMS4SvLZVku2zfc8wL6aX5FBubEcrQ";
		
		RefreshTokenDom refreshTokenDomMock = new RefreshTokenDom();
		refreshTokenDomMock.setValue(RandomStringUtils.randomAlphabetic(5));
		
		// When
		when(iUsuarioRepository.login(securityRqDTO.getAccessKeyId(), securityRqDTO.getSecretAccessKey())).thenReturn(userDomMock);
		when(jwtTokenService.doGenerateToken(userToken)).thenReturn(jwtMock);
		when(iRefreshTokenService.doGenerateRefreshToken(Mockito.any(UserDom.class), Mockito.eq(trackingId))).thenReturn(refreshTokenDomMock);
		
		ResponseRsDTO<SecurityRsDTO> result = iUsuarioService.login(securityRqDTO);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).withFailMessage("El success debe ser exitoso, el resultado fue <%s>", result.isSuccess()).isTrue();
		assertThat(result.getTrackingId()).withFailMessage("El trackingId no deber ser nulo o vacio, el resultado fue <%s>", result.getTrackingId()).isNotNull().isNotEmpty();
		assertThat(result.getObject()).withFailMessage("El objeto no debe ser nulo, el resultado fue <%s>", result.getObject()).isNotNull();
		assertThat(result.getObject().getToken()).withFailMessage("El token no deber ser nulo o vacio, el resultado fue <%s>", result.getObject().getToken()).isNotNull().isNotEmpty();
		assertThat(result.getErrorCode()).withFailMessage("El codigo debe ser nulo, el resultado fue <%s>", result.getErrorCode()).isNull();
		
		verify(iUsuarioRepository).login(securityRqDTO.getAccessKeyId(), securityRqDTO.getSecretAccessKey());
		verify(jwtTokenService).doGenerateToken(userToken);
		verify(iRefreshTokenService).doGenerateRefreshToken(Mockito.any(UserDom.class),  Mockito.eq(trackingId));

	}
	
	@DisplayName("Login with user not found")
	@Test
	void loginUserUnknow() {
		
		//Given
		String trackingId = String.valueOf(System.currentTimeMillis());

		SecurityRqDTO securityRqDTO = new SecurityRqDTO();
		securityRqDTO.setAccessKeyId("XP92870");
		securityRqDTO.setSecretAccessKey("12345678");
		securityRqDTO.setTrackingId(trackingId);
		
		// Mock
		UserDom usuarioMock = null;
		
		// When
		when(iUsuarioRepository.login(securityRqDTO.getAccessKeyId(), securityRqDTO.getSecretAccessKey())).thenReturn(usuarioMock);

		ResponseRsDTO<SecurityRsDTO> result = iUsuarioService.login(securityRqDTO);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).withFailMessage("El success debe ser falso, el resultado fue <%s>", result.isSuccess()).isFalse();
		assertThat(result.getTrackingId()).withFailMessage("El trackingId No de beser nulo o vacio, el resultado fue <%s>", result.getTrackingId()).isNotNull().isNotEmpty();
		assertThat(result.getErrorCode()).withFailMessage("El codigo debe ser ErrorCode.USER_UNKNOW: <%s>, el resultado fue <%s>", ErrorCode.USER_INVALID.getCode(), result.getErrorCode()).isNotNull().isNotEmpty();
		
		verify(iUsuarioRepository).login(securityRqDTO.getAccessKeyId(), securityRqDTO.getSecretAccessKey());
		
	}
	
	@DisplayName("Usuario Bloqueado")
	@Test
	void usuarioBloqueado() {
		
		//Given
		String trackingId = String.valueOf(System.currentTimeMillis());

		SecurityRqDTO securityRqDTO = new SecurityRqDTO();
		securityRqDTO.setAccessKeyId("XP88888");
		securityRqDTO.setSecretAccessKey("88888888");
		securityRqDTO.setTrackingId(trackingId);
		
		// Mock
		UserDom usuarioMock = new UserDom();
		usuarioMock.setLocked(Boolean.TRUE);
		
		// When
		when(iUsuarioRepository.login(securityRqDTO.getAccessKeyId(), securityRqDTO.getSecretAccessKey())).thenReturn(usuarioMock);

		ResponseRsDTO<SecurityRsDTO> result = iUsuarioService.login(securityRqDTO);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).withFailMessage("El success debe ser falso, el resultado fue <%s>", result.isSuccess()).isFalse();
		assertThat(result.getTrackingId()).withFailMessage("El trackingId No debe ser nulo o vacio, el resultado fue <%s>", result.getTrackingId()).isNotNull().isNotEmpty();
		assertThat(result.getErrorCode()).withFailMessage("El codigo debe ser ErrorCode.USER_LOCKED: <%s>, el resultado fue <%s>", ErrorCode.USER_LOCKED.getCode(), result.getErrorCode()).isNotNull().isNotEmpty();
		
		// Verify
		verify(iUsuarioRepository).login(securityRqDTO.getAccessKeyId(), securityRqDTO.getSecretAccessKey());
		
	}
	
	
	@DisplayName("refreshToken, the result is success")
	@Test
	void refreshTokenSuccess() {
		
		//Given
		TokenCredentialRqDTO tokenCredentialRqDTO = new TokenCredentialRqDTO();
		tokenCredentialRqDTO.setRefreshToken(RandomStringUtils.randomAlphanumeric(5));
		tokenCredentialRqDTO.setJwt(RandomStringUtils.randomAlphabetic(5));
		tokenCredentialRqDTO.setTrackingId(RandomStringUtils.randomAlphabetic(5));

		// Mock
		UserDom usuarioMock = new UserDom();
		usuarioMock.setAccessKeyId(RandomStringUtils.randomAlphanumeric(5));
		usuarioMock.setSecretAccessKey(RandomStringUtils.randomAlphanumeric(5));
		usuarioMock.setRoleDom(new RoleDom());
		usuarioMock.getRoleDom().setValue("ROLE_USER");
		
		RefreshTokenDom refreshTokenDomMock = new RefreshTokenDom();
		refreshTokenDomMock.setUser(usuarioMock);
		refreshTokenDomMock.setValue(tokenCredentialRqDTO.getRefreshToken());
		
		String newJwt = RandomStringUtils.randomAlphabetic(5);
		
		// When
		when(iRefreshTokenService.getByRefreshToken(tokenCredentialRqDTO.getRefreshToken(), tokenCredentialRqDTO.getTrackingId())).thenReturn(refreshTokenDomMock);
		when(jwtTokenService.doGenerateToken(Mockito.any(UserDetails.class))).thenReturn(newJwt);
		ResponseRsDTO<SecurityRsDTO> result = iUsuarioService.refreshToken(tokenCredentialRqDTO);
		
		// Then
		assertNotNull(result);
		assertTrue(result.isSuccess());
		assertThat(result.getObject()).isNotNull();
		assertThat(result.getObject().getRefreshToken()).isNotNull().isNotEmpty();
		assertThat(result.getObject().getRefreshToken()).isEqualTo(tokenCredentialRqDTO.getRefreshToken());
		assertThat(result.getObject().getToken()).isNotNull().isNotEmpty();
		
		// Verify
		verify(iRefreshTokenService).getByRefreshToken(tokenCredentialRqDTO.getRefreshToken(), tokenCredentialRqDTO.getTrackingId());
		verify(jwtTokenService).doGenerateToken(Mockito.any(UserDetails.class));

	}
}
