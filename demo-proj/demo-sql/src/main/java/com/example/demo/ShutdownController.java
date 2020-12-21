package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ShutdownService;

@RestController
public class ShutdownController {

	@Autowired
	private ShutdownService shutdownService;

	@GetMapping("/shutdown")
	public void shutdownContext() {
		shutdownService.shutdown();
	}

}