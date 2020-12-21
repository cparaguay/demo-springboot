package com.example.demo.util.log4j;

import java.text.MessageFormat;


public class Log4jUtil {

	private static final String INIT = "[[{0}[[";
	private static final String END = "]]{0}]]";
	
	private Log4jUtil() {
	}
	
	public static String initLog(String metodo, Object ...params) {
		String text = MessageFormat.format(INIT, metodo);
		return MessageFormat.format(text, params);
	}
	

	public static String endLog(String metodo, Object ...params) {
		String text = MessageFormat.format(END, metodo);
		return MessageFormat.format(text, params);	}
}
