/**
 * Copyright (c) - 2012, Dion Global Solutions. Company confidential.
 */
package com.utils;

import javax.management.MXBean;

@MXBean
public interface LogUtilMBean {
	void setLogLevel(String className, String logLevel);

	String getLogLevel(String className);
}
