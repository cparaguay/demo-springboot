package com.example.demo.it;

import java.io.UnsupportedEncodingException;

import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.dto.SecurityRqDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;

public class BaseIntegrationIT {


	protected <T> T fromJSON(ResultActions result,  Type typeOfT) throws JsonSyntaxException, UnsupportedEncodingException {
		return new Gson().fromJson(result.andReturn().getResponse().getContentAsString(), typeOfT);
	}
	
	public static String toJSON(SecurityRqDTO securityRq) {
		return new Gson().toJson(securityRq);
	}
	
	protected <T> T fromJson(ResultActions result,  Class<T> valueType) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
		return  new ObjectMapper().readValue(result.andReturn().getResponse().getContentAsString(), valueType);
	}
	
}
