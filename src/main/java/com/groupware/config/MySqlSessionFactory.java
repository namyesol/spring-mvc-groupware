package com.groupware.config;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MySqlSessionFactory implements ApplicationContextAware {
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextHolder.INNER_CONTEXT_RESOURCE.setApplicationContext(applicationContext); 
		
	}
	
	private static class ApplicationContextHolder {
		
		private static final InnerContextResource INNER_CONTEXT_RESOURCE = new InnerContextResource();
	
		private ApplicationContextHolder() {
			super();
		}
	}
	
	private static final class InnerContextResource {
		private ApplicationContext applicationContext;
		
		private InnerContextResource() {
			super();
		}
		
		private void setApplicationContext(ApplicationContext applicationContext) {
			this.applicationContext = applicationContext;
		}
	}
	
	private static ApplicationContext getApplicationContext() {
		return ApplicationContextHolder.INNER_CONTEXT_RESOURCE.applicationContext;
	}
	
	public static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = getApplicationContext().getBean(SqlSessionFactory.class);
		return sqlSessionFactory.openSession();
	}
	
}
