package com.example.demo.dto.error;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorInfoRqDTO {

	private int status;
	
	private String error;
	
	private String trace;
	
	private String message;
	
	private String uriRequested;
		
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss z", timezone = "GMT-05:00")
	private Date timeStamp;

	private String idTracking;
	
}
