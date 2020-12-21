package com.example.demo.dto.error;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorInfoRsDTO {

	private int status;
	
	private String error;
		
	private String uriRequested;
		
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss z", timezone = "GMT-05:00")
	private Date timeStamp;

    @JsonInclude(value = Include.NON_NULL)
	private String idTracking;

	public ErrorInfoRsDTO(int status, String error, String uriRequested, Date timeStamp) {
		super();
		this.status = status;
		this.error = error;
		this.uriRequested = uriRequested;
		this.timeStamp = timeStamp;
	}

	
}
