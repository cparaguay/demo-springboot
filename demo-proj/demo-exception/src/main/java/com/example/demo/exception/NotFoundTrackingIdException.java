package com.example.demo.exception;

@SuppressWarnings("serial")
public class NotFoundTrackingIdException extends Exception {

	public NotFoundTrackingIdException() {
		super("Not found tracking id");
	}
}
