package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.path.ExceptionControllerPath;


@RestController
@RequestMapping(value = ExceptionControllerPath.RESOURCE)
public class ExceptionController {

	@GetMapping(value = ExceptionControllerPath.NULL_POINTER_EXCEPTION)
	public void nullPointerException() {
		throw new NullPointerException("ERROR");
	}
	
	@GetMapping(value = ExceptionControllerPath.NUMBER_FORMAT_EXCEPTION)
	public void numberFormatException() {
		throw new NumberFormatException("ERROR");
	}
	
	@GetMapping(value = ExceptionControllerPath.RUN_TIME_EXCEPTION)
	public void runtimeException() {
		throw new RuntimeException("ERROR");
	}
}
