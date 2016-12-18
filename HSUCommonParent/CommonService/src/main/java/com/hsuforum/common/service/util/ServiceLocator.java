package com.hsuforum.common.service.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Service locator for get bean of spring
 * 
 * @author Marvin
 *
 */
@SuppressWarnings("rawtypes")
public class ServiceLocator implements ApplicationListener {
	public static ApplicationContext applicationContext;

	/**
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(final ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
		}
	}

	/**
	 * Get ApplicationContext
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Set ApplicationContext
	 * 
	 * @param applicationContext
	 */
	public static void setApplicationContext(final ApplicationContext applicationContext) {
		ServiceLocator.applicationContext = applicationContext;
	}

	/**
	 * Get bean by name
	 * 
	 * @param name
	 * @return
	 */
	public static Object getBean(final String name) {
		return applicationContext.getBean(name);
	}
}
