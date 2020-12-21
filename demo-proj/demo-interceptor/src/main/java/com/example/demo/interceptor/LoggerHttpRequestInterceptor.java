package com.example.demo.interceptor;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.exception.NotFoundTrackingIdException;
import com.example.demo.util.log4j.RequestLog;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class LoggerHttpRequestInterceptor implements HandlerInterceptor{


	public static final String TRACKING_ID = "tracking-id";

	private static final String REQUEST_MESSAGE_FORMAT = "request: {0}";
	private static final String START_TIME_ATTRIBUTE_NAME = "startTime";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws NotFoundTrackingIdException {
		long startTime = System.currentTimeMillis();
		request.setAttribute(START_TIME_ATTRIBUTE_NAME, startTime);
		return true;
	}
	
	/**
	 * Metodo para copiar generar header en un mapa
	 * @param request
	 * @return
	 * @throws NotFoundTrackingIdException 
	 */
	private Map<String, String> copiarHeaders(HttpServletRequest request)  {
		Enumeration<String> headerNames = request.getHeaderNames();
		Map<String, String> map = new HashMap<>();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			if(StringUtils.equals(headerName, TRACKING_ID)) {
				String headerValue = request.getHeader(headerName);
				map.put(headerName, headerValue);	
			}
		}
		
		return map;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE_NAME);
		
		RequestLog requestLog = new RequestLog();
		requestLog.setUrl(request.getRequestURL().toString());
		requestLog.setHeaders(copiarHeaders(request));		
		requestLog.setMethod(request.getMethod());
		requestLog.setTiempoTotal(System.currentTimeMillis() - startTime);
		
		String jsonRequest = StringEscapeUtils.unescapeJava(ToStringBuilder.reflectionToString(requestLog, ToStringStyle.JSON_STYLE));
		
		log.info(MessageFormat.format(REQUEST_MESSAGE_FORMAT, jsonRequest));
	}
}
