package com.example.demo.service.utils;


import org.apache.commons.lang3.BooleanUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ValidateObject <T> {

	private boolean success;
	private String error;
	@NonNull private T object;
	
	
	public void setSuccess(boolean success){
		if(BooleanUtils.isFalse(success)) {
			this.object = null;
		}
		this.success = success;
	}
	
	
}
