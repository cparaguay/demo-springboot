package com.example.demo.controller.handler.error;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.controller.utils.ControllerHeaderUtils;
import com.example.demo.dto.error.ErrorInfoRqDTO;
import com.example.demo.dto.error.ErrorInfoRsDTO;
import com.example.demo.exception.NotFoundTrackingIdException;
import com.example.demo.service.IErrorInfoService;
import static com.example.demo.util.log4j.Log4jUtil.*;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class ErrorExceptionHandler {

	@Autowired
	private IErrorInfoService iErrorInfoService;
	
   @ExceptionHandler({Exception.class, RuntimeException.class})
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   public ErrorInfoRsDTO exceptionGeneral(HttpServletRequest request, Exception e) {
	   
	   String idTracking = request.getHeader(ControllerHeaderUtils.KEY_TRACKING_ID_HEADER);
	   	   
	   log.debug(initLog("exceptionGeneral()| idTracking: {0}", idTracking));
	   
	   log.error("idTracking: {}", idTracking, e.getMessage(), e);

	   ErrorInfoRqDTO errorDTO = new ErrorInfoRqDTO(
			   HttpStatus.INTERNAL_SERVER_ERROR.value(),
			   HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), 
			   e.toString(), 
			   String.valueOf(e.getMessage()), 
			   request.getRequestURI(), new Date(), idTracking);
	   
	   ErrorInfoRsDTO ErrorInfoRsDTO = iErrorInfoService.save(errorDTO);
	   
	   log.debug(endLog("exceptionGeneral()| idTracking: {0}", idTracking));
	   
       return ErrorInfoRsDTO;
   }
   
   @ExceptionHandler(NotFoundTrackingIdException.class)
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   public ErrorInfoRsDTO notFoundTrackingIdException(HttpServletRequest request, Exception e) {
	   log.error(e.getMessage());
	   return new ErrorInfoRsDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), request.getRequestURI(), new Date());
   }
   
}
