package com.groupware.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

	private static class ApplicationContextHolder {
		private static final InnerContextProvider INNER_CONTEXT_PROVIDER = new InnerContextProvider();
		
		private ApplicationContextHolder() {}
	}
	
	private static final class InnerContextProvider {
		private ApplicationContext applicationContext;
		
		private InnerContextProvider() {}
		
		private void setApplicationContext(ApplicationContext applicationContext) {
			this.applicationContext = applicationContext;
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextHolder.INNER_CONTEXT_PROVIDER.setApplicationContext(applicationContext); 
	}
	
	public static ApplicationContext getApplicationContext() {
		return ApplicationContextHolder.INNER_CONTEXT_PROVIDER.applicationContext;
	}
}
