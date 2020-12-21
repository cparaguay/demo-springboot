package com.example.demo.it;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.DemoApplication;
import com.example.demo.config.utils.Profiles;
import com.example.demo.controller.path.SecurityControllerPath;
import com.example.demo.dto.ResponseRsDTO;
import com.example.demo.dto.SecurityRqDTO;
import com.example.demo.dto.SecurityRsDTO;
import com.example.demo.service.error.ErrorCode;
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
public class LoginIntegrationIT extends BaseIntegrationIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	String pathLogin = StringUtils.join(SecurityControllerPath.RESOURCE_PATH, SecurityControllerPath.LOGIN.PATH);


	@DisplayName("Login, success")
	@Test
	public void successLogin() throws Exception {
				
		// Given
		SecurityRqDTO securityRq = new SecurityRqDTO();
		securityRq.setAccessKeyId("XP92870");
		securityRq.setSecretAccessKey("12345678");
		
		// When
		ResultActions result = mockMvc.perform(post(pathLogin).contentType(APPLICATION_JSON_VALUE).content(toJSON(securityRq))).andExpect(status().isOk());
		
		// Then			   
		ResponseRsDTO<SecurityRsDTO> responseObject = fromJSON(result, new TypeToken<ResponseRsDTO<SecurityRsDTO>>(){}.getType());
		
		assertTrue(responseObject.isSuccess(), "This result should success");
		assertNotNull(responseObject.getObject(), "El object debe ser no nulo");
		assertNotNull(responseObject.getObject().getToken(), "El jwt no debe ser nulo");
		assertNotNull(responseObject.getObject().getRefreshToken(), "The refresh token does'nt should be null");
		assertNull(responseObject.getErrorCode(), "El error debe ser nulo");
		assertNotNull(responseObject.getTrackingId(), "El trackingId no debe ser nulo");

	}
	
	@DisplayName("Login, failed login for Invalid user")
	@Test
	public void failedLogin() throws Exception {
				
		// Given
		SecurityRqDTO securityRq = new SecurityRqDTO();
		securityRq.setAccessKeyId("XP1234567");
		securityRq.setSecretAccessKey("12345678");
		
		// When
		ResultActions result = mockMvc.perform(post(pathLogin).contentType(APPLICATION_JSON_VALUE).content(toJSON(securityRq))).andExpect(status().isOk());
		
		// Then			   
		ResponseRsDTO<SecurityRsDTO> responseObject = fromJSON(result, new TypeToken<ResponseRsDTO<SecurityRsDTO>>(){}.getType());
		
		assertFalse(responseObject.isSuccess(), "El resultado debe ser falso.");
		assertNull(responseObject.getObject(), "El object debe ser nulo.");
		assertNotNull(responseObject.getErrorCode(), "El error debe ser no nulo.");
		assertEquals(ErrorCode.USER_INVALID.getCode(), responseObject.getErrorCode());
		assertNotNull(responseObject.getTrackingId(), "El trackingId no debe ser nulo");
	}
	
	@DisplayName("Login, failed login for locked user")
	@Test
	public void loginLockedUser() throws Exception {
		
		// Given
		SecurityRqDTO securityRq = new SecurityRqDTO();
		securityRq.setAccessKeyId("XP88888");
		securityRq.setSecretAccessKey("88888888");
		
		// When
		ResultActions result = mockMvc.perform(post(pathLogin).contentType(APPLICATION_JSON_VALUE).content(toJSON(securityRq))).andExpect(status().isOk());
		
		// Then			   
		ResponseRsDTO<SecurityRsDTO> responseObject = fromJSON(result, new TypeToken<ResponseRsDTO<SecurityRsDTO>>(){}.getType());
		
		assertFalse(responseObject.isSuccess(), "El resultado debe ser falso.");
		assertNull(responseObject.getObject(), "El object debe ser nulo.");
		assertNotNull(responseObject.getErrorCode(), "El error debe ser no nulo.");
		assertEquals(ErrorCode.USER_LOCKED.getCode(), responseObject.getErrorCode());
		assertNotNull(responseObject.getTrackingId(), "El trackingId no debe ser nulo");
	}
	
	@DisplayName("Login, login with empty field accessKeyId")
	@Test
	void loginWithEmtyFiledAccessKeyId() throws Exception {
		
		// Given
		SecurityRqDTO securityRq = new SecurityRqDTO();
		securityRq.setAccessKeyId(StringUtils.EMPTY);
		securityRq.setSecretAccessKey("12345678");
		
		// When
		ResultActions result = mockMvc.perform(post(pathLogin).contentType(APPLICATION_JSON_VALUE).content(toJSON(securityRq))).andExpect(status().isOk());
		
		// Then			   
		ResponseRsDTO<SecurityRsDTO> responseObject = fromJSON(result, new TypeToken<ResponseRsDTO<SecurityRsDTO>>(){}.getType());
		
		assertFalse(responseObject.isSuccess(), "El resultado debe ser false");
		assertNull(responseObject.getObject(), "El object debe ser nulo");
		assertEquals(ErrorCode.EMPTY_FIELDS.getCode(), responseObject.getErrorCode());
		assertNotNull(responseObject.getTrackingId(), "El trackingId no debe ser nulo");
	}
	
	@DisplayName("Login, login with empty field secretAccessKey")
	@Test
	void loginWithEmtyFiledSecredAccessKey() throws Exception {
		
		// Given
		SecurityRqDTO securityRq = new SecurityRqDTO();
		securityRq.setAccessKeyId("XP1234567");
		securityRq.setSecretAccessKey(StringUtils.EMPTY);
		
		// When
		ResultActions result = mockMvc.perform(post(pathLogin).contentType(APPLICATION_JSON_VALUE).content(toJSON(securityRq))).andExpect(status().isOk());
		
		// Then			   
		ResponseRsDTO<SecurityRsDTO> responseObject = fromJSON(result, new TypeToken<ResponseRsDTO<SecurityRsDTO>>(){}.getType());
		
		assertFalse(responseObject.isSuccess(), "El resultado debe ser false");
		assertNull(responseObject.getObject(), "El object debe ser nulo");
		assertEquals(ErrorCode.EMPTY_FIELDS.getCode(), responseObject.getErrorCode());
		assertNotNull(responseObject.getTrackingId(), "El trackingId no debe ser nulo");
	}
	

}
