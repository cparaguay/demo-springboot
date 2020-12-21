package com.example.demo.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.exception.NotFoundTrackingIdException;

public class ValidateTrackingIdHttpRequestInterceptor implements HandlerInterceptor {

	public static final String TRACKING_ID = "tracking-id";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		validateTrackingId(request);
		return true;
	}

	private void validateTrackingId(HttpServletRequest request) throws NotFoundTrackingIdException {

		Enumeration<String> headerNames = request.getHeaderNames();
		
		boolean trakingId = Boolean.FALSE;

		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			if (StringUtils.equals(headerName, TRACKING_ID)) {
				trakingId = Boolean.TRUE;
				break;
			}
		}

		if (BooleanUtils.isFalse(trakingId)) {
			throw new NotFoundTrackingIdException();
		}

	}
}
