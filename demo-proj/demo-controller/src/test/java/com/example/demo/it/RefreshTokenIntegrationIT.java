package com.example.demo.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.DemoApplication;
import com.example.demo.config.utils.Profiles;
import com.example.demo.controller.path.SecurityControllerPath;
import com.example.demo.controller.utils.ControllerHeaderUtils;
import com.example.demo.dto.ResponseRsDTO;
import com.example.demo.dto.SecurityRqDTO;
import com.example.demo.dto.SecurityRsDTO;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author dalozz
 * 
 * 	@ActiveProfiles: Sirve para cargar un conjunto de propiedades mediante un perfil
 * 				     definido en la sintaxis del archivo de propiedades de la app.
 * 
 *  @AutoConfigureMockMvc: Sirve para agregar una instancia de MockMvc.
 *
 */
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = Profiles.IT)
public class RefreshTokenIntegrationIT extends BaseIntegrationIT{
	
	@Autowired
	private MockMvc mockMvc;
	
	String pathLogin = StringUtils.join(SecurityControllerPath.RESOURCE_PATH, SecurityControllerPath.LOGIN.PATH);

	@DisplayName("RefreshToken, success")
	@Test
	void refreshTokenSucccess() throws Exception {
		
		// Endpoint
		String pathRefreshToken = StringUtils.join(SecurityControllerPath.RESOURCE_PATH, SecurityControllerPath.REFRESH_TOKEN.PATH);
		
		// Given
		SecurityRqDTO securityRq = new SecurityRqDTO();
		securityRq.setAccessKeyId("XP92870");
		securityRq.setSecretAccessKey("12345678");
		
		
		ResultActions resultLogin = mockMvc.perform(post(pathLogin).contentType(APPLICATION_JSON_VALUE).content(toJSON(securityRq))).andExpect(status().isOk());

		ResponseRsDTO<SecurityRsDTO> responseLogin = fromJSON(resultLogin, new TypeToken<ResponseRsDTO<SecurityRsDTO>>(){}.getType());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(ControllerHeaderUtils.KEY_REFRESH_TOKEN_HEADER, responseLogin.getObject().getRefreshToken());
		httpHeaders.add(ControllerHeaderUtils.KEY_AUTHORIZATION_HEADER, responseLogin.getObject().getToken());
		httpHeaders.add(ControllerHeaderUtils.KEY_TRACKING_ID_HEADER, responseLogin.getTrackingId());
		
		// When
		ResultActions result = mockMvc.perform(post(pathRefreshToken).contentType(APPLICATION_JSON_VALUE).headers(httpHeaders)).andExpect(status().isOk());
		
		// Then			   
		ResponseRsDTO<SecurityRsDTO> responseObject = fromJSON(result, new TypeToken<ResponseRsDTO<SecurityRsDTO>>(){}.getType());
		
		assertTrue(responseObject.isSuccess(), "This result should success");
		assertNotNull(responseObject.getObject(), "El object debe ser no nulo");
		assertNotNull(responseObject.getObject().getToken(), "El jwt no debe ser nulo");
		assertNotNull(responseObject.getObject().getRefreshToken(), "The refresh token does'nt should be null");
		assertThat(responseObject.getObject().getRefreshToken()).isEqualTo(responseLogin.getObject().getRefreshToken());
		assertNull(responseObject.getErrorCode(), "El error debe ser nulo");
		assertNotNull(responseObject.getTrackingId(), "El trackingId no debe ser nulo");
	}
	
	@DisplayName("RefreshToken, the refreshToken is unknow")
	@Test
	void refreshTokenIsUnknow() throws Exception {
		
		// Endpoint
		String pathRefreshToken = StringUtils.join(SecurityControllerPath.RESOURCE_PATH, SecurityControllerPath.REFRESH_TOKEN.PATH);
		
		// Given
		SecurityRqDTO securityRq = new SecurityRqDTO();
		securityRq.setAccessKeyId("XP92870");
		securityRq.setSecretAccessKey("12345678");
		String refreshTokenUnknow = RandomStringUtils.randomAlphanumeric(5);
		
		
		ResultActions resultLogin = mockMvc.perform(post(pathLogin).contentType(APPLICATION_JSON_VALUE).content(toJSON(securityRq))).andExpect(status().isOk());

		ResponseRsDTO<SecurityRsDTO> responseLogin = fromJSON(resultLogin, new TypeToken<ResponseRsDTO<SecurityRsDTO>>(){}.getType());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(ControllerHeaderUtils.KEY_REFRESH_TOKEN_HEADER, refreshTokenUnknow);
		httpHeaders.add(ControllerHeaderUtils.KEY_AUTHORIZATION_HEADER, responseLogin.getObject().getToken());
		httpHeaders.add(ControllerHeaderUtils.KEY_TRACKING_ID_HEADER, responseLogin.getTrackingId());
		
		// When
		ResultActions result = mockMvc.perform(post(pathRefreshToken).contentType(APPLICATION_JSON_VALUE).headers(httpHeaders)).andExpect(status().isOk());
		
		// Then			   
		ResponseRsDTO<SecurityRsDTO> responseObject = fromJSON(result, new TypeToken<ResponseRsDTO<SecurityRsDTO>>(){}.getType());
		
		assertFalse(responseObject.isSuccess(), "This result should success");
		assertNull(responseObject.getObject(), "El object debe ser nulo");
		assertNotNull(responseObject.getErrorCode(), "El error debe ser no nulo");
		assertNotNull(responseObject.getTrackingId(), "El trackingId debe ser no nulo");
	}
}
