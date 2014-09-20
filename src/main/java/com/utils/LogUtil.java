package com.utils;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LogUtil implements LogUtilMBean {
	private static final String LOGUTIL_HIERARCHY_LOG = "com.dfferentia.util:type=LogUtil";
	private static final Logger LOG = Logger.getLogger(LogUtil.class);

	public static void initialize() {
		registerLogUtilMBean();
	}

	private static void registerLogUtilMBean() {
		MBeanServer server = findMBeanServer();
		if (server == null) {
			LOG.error("[LogUtil MBean] Error: Could not find MBeanServer");
			return;
		}
		try {
			final String mboName = LOGUTIL_HIERARCHY_LOG + String.format("[%X]", Long.valueOf(System.nanoTime()));
			final ObjectName mbo = new ObjectName(mboName);
			server.registerMBean(new LogUtil(), mbo);
			LOG.trace("[LogUtil MBean] Success");
		} catch (Exception e) {
			LOG.error("[LogUtil MBean] Error: ", e);
		}
	}

	private static MBeanServer findMBeanServer() {
		List<MBeanServer> serverList = MBeanServerFactory.findMBeanServer(null);
		if (serverList != null && serverList.size() > 0) {
			return serverList.get(0);
		}
		return ManagementFactory.getPlatformMBeanServer();
	}

	@Override
	public void setLogLevel(String className, String logLevel) {
		Logger logger = getLogger(className);
		Level level = Level.toLevel(logLevel);
		logger.setLevel(level);
		LOG.info("UPDATE LogLevel [" + logger + "] to [" + level + "]");
	}

	@Override
	public String getLogLevel(String className) {
		Logger logger = getLogger(className);
		return logger.getEffectiveLevel().toString();
	}

	private Logger getLogger(String className) {
		if (className == null || "".equals(className.trim())) {
			return Logger.getRootLogger();
		}
		return Logger.getLogger(className);
	}
}
