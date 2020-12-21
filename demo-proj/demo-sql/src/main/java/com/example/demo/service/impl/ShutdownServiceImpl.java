package com.example.demo.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.example.demo.service.ShutdownService;

import lombok.extern.log4j.Log4j2;

@Service
@EnableAsync
@Log4j2
public class ShutdownServiceImpl implements ShutdownService, ApplicationContextAware{

	private ApplicationContext context;

	public void shutdownContext() {
		((ConfigurableApplicationContext) context).close();
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.context = ctx;
	}

	@Async
	@Override
	public void shutdown() {
		try {
			Thread.sleep(5000l);
			shutdownContext();
		} catch (RuntimeException e) {
	        log.warn("Closing application while CDR still in queue");
		} catch (InterruptedException e) {
	        log.warn("Closing application while CDR still in queue");
		}


	}

}
