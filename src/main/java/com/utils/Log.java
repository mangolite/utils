package com.utils;

import org.apache.log4j.Logger;

public class Log {
	// Register the beans when the class loads
	static {
		LogUtil.initialize();
	}

	private Logger logger;

	public Log() {
		StackTraceElement[] elements = new Throwable().getStackTrace();
		this.logger = Logger.getLogger(elements[1].getClassName());
	}

	public void trace(Object msg) {
		this.logger.trace(msg);
	}

	public void debug(Object msg) {
		this.logger.debug(msg);
	}

	public void info(Object msg) {
		this.logger.info(msg);
	}

	public void warn(Object msg) {
		this.logger.warn(msg);
	}

	public void error(Object msg) {
		this.logger.error(msg);
	}

	public void severe(Object msg) {
		this.logger.error(msg);
	}

	public void fatal(Object msg) {
		this.logger.fatal(msg);
	}

	public void trace(Object msg, Throwable thrown) {
		this.logger.trace(msg, thrown);
	}

	public void debug(Object msg, Throwable thrown) {
		this.logger.debug(msg, thrown);
	}

	public void info(Object msg, Throwable thrown) {
		this.logger.info(msg, thrown);
	}

	public void warn(Object msg, Throwable thrown) {
		this.logger.warn(msg, thrown);
	}

	public void error(Object msg, Throwable thrown) {
		this.logger.error(msg, thrown);
	}

	public void severe(Object msg, Throwable thrown) {
		this.logger.error(msg, thrown);
	}

	public void fatal(Object msg, Throwable thrown) {
		this.logger.fatal(msg, thrown);
	}

	public boolean isTraceEnabled() {
		return this.logger.isTraceEnabled();
	}
}
