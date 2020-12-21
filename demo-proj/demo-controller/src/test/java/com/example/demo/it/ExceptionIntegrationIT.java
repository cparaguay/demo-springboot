package com.example.demo.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.DemoApplication;
import com.example.demo.config.utils.Profiles;
import com.example.demo.controller.path.ExceptionControllerPath;
import com.example.demo.controller.utils.ControllerHeaderUtils;
import com.example.demo.dto.error.ErrorInfoRsDTO;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = Profiles.IT)
public class ExceptionIntegrationIT extends BaseIntegrationIT {

	@Autowired
	private MockMvc mockMvc;
	
	@DisplayName("NullPointerException")
	@Test
	void errorNullException() throws Exception {
		
		// Endpoint
		String path = StringUtils.join(ExceptionControllerPath.RESOURCE, ExceptionControllerPath.NULL_POINTER_EXCEPTION);
		
		// Given
		
		// When
		ResultActions result = mockMvc.perform(get(path).header(ControllerHeaderUtils.KEY_TRACKING_ID_HEADER, RandomStringUtils.randomAlphabetic(5)));
		
		// Then		
		ErrorInfoRsDTO errorInfo = fromJson(result, ErrorInfoRsDTO.class);

		result.andExpect(status().isInternalServerError()).andExpect(value -> assertTrue(value.getResolvedException() instanceof  NullPointerException));
		
		assertEquals(errorInfo.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		assertNotNull(errorInfo.getError());
		assertEquals(errorInfo.getError(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		assertNotNull(errorInfo.getTimeStamp());
		assertNotNull(errorInfo.getUriRequested());
	}
	
	@DisplayName("NumberFormatException")
	@Test
	void errorNumberFormatException() throws Exception {
		
		// Endpoint
		String path = StringUtils.join(ExceptionControllerPath.RESOURCE, ExceptionControllerPath.NUMBER_FORMAT_EXCEPTION);
		
		// Given
		
		// When
		ResultActions result = mockMvc.perform(get(path).header(ControllerHeaderUtils.KEY_TRACKING_ID_HEADER, RandomStringUtils.randomAlphabetic(5)));
		
		// Then		
		ErrorInfoRsDTO errorInfoRs = fromJson(result, ErrorInfoRsDTO.class);

		result.andExpect(status().isInternalServerError()).andExpect(value -> assertTrue(value.getResolvedException() instanceof  NumberFormatException));
		
		assertEquals(errorInfoRs.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		assertNotNull(errorInfoRs.getError());
		assertEquals(errorInfoRs.getError(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		assertNotNull(errorInfoRs.getTimeStamp());
		assertNotNull(errorInfoRs.getUriRequested());
	}
	
	@DisplayName("RuntimeException")
	@Test
	void errorRuntimeException() throws Exception {
		
		// Endpoint
		String path = StringUtils.join(ExceptionControllerPath.RESOURCE, ExceptionControllerPath.NUMBER_FORMAT_EXCEPTION);
		
		// Given
		
		// When
		ResultActions result = mockMvc.perform(get(path).header(ControllerHeaderUtils.KEY_TRACKING_ID_HEADER, RandomStringUtils.randomAlphabetic(5)));
		
		// Then		
		ErrorInfoRsDTO errorInfoRs = fromJson(result, ErrorInfoRsDTO.class);

		result.andExpect(status().isInternalServerError()).andExpect(value -> assertTrue(value.getResolvedException() instanceof  RuntimeException));
		
		assertEquals(errorInfoRs.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		assertNotNull(errorInfoRs.getError());
		assertEquals(errorInfoRs.getError(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		assertNotNull(errorInfoRs.getTimeStamp());
		assertNotNull(errorInfoRs.getUriRequested());
	}
}
