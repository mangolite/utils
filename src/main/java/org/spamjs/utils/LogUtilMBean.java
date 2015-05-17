package org.spamjs.utils;

import javax.management.MXBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface LogUtilMBean.
 */
@MXBean
public interface LogUtilMBean {
	
	/**
	 * Sets the log level.
	 *
	 * @param className the class name
	 * @param logLevel the log level
	 */
	void setLogLevel(String className, String logLevel);

	/**
	 * Gets the log level.
	 *
	 * @param className the class name
	 * @return the log level
	 */
	String getLogLevel(String className);
}
