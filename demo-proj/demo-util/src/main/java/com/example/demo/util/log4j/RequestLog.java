package com.example.demo.util.log4j;

import java.util.Map;

public class RequestLog {
	
	private String url;
	private Map<String, String> headers;
	private String method;
	private Long tiempoTotal;

	public String getUrl() {
		return url;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getMethod() {
		return method;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getTiempoTotal() {
		return tiempoTotal;
	}

	public void setTiempoTotal(Long tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}
}
