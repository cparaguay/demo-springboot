package com.example.demo.service.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

	USER_INVALID("100"),
	USER_LOCKED("102"),
	EMPTY_FIELDS("101");
	
	private String code;

}