package org.spamjs.utils;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Log {

	private Logger logger;

	public Log() {
		StackTraceElement[] elements = new Throwable().getStackTrace();
		this.logger = Logger.getLogger(elements[1].getClassName());
	}

	public void trace(Object msg) {
		this.logger.log(Level.FINEST, ArgUtil.parseAsString(msg));
	}

	public void debug(Object msg) {
		this.logger.log(Level.FINER,ArgUtil.parseAsString(msg));
	}

	public void info(Object msg) {
		this.logger.log(Level.INFO,ArgUtil.parseAsString(msg));
	}

	public void warn(Object msg) {
		this.logger.log(Level.WARNING,ArgUtil.parseAsString(msg));
	}

	public void error(Object msg) {
		this.logger.log(Level.FINE,ArgUtil.parseAsString(msg));
	}

	public void severe(Object msg) {
		this.logger.log(Level.SEVERE,ArgUtil.parseAsString(msg));
	}

	public void fatal(Object msg) {
		this.logger.log(Level.SEVERE,ArgUtil.parseAsString(msg));
	}

	public void trace(Object msg, Throwable thrown) {
		this.logger.log(Level.FINEST, ArgUtil.parseAsString(msg),thrown);
	}

	public void debug(Object msg, Throwable thrown) {
		this.logger.log(Level.FINER, ArgUtil.parseAsString(msg),thrown);
	}

	public void info(Object msg, Throwable thrown) {
		this.logger.log(Level.INFO,ArgUtil.parseAsString(msg));
	}

	public void warn(Object msg, Throwable thrown) {
		this.logger.log(Level.WARNING,ArgUtil.parseAsString(msg));
	}

	public void error(Object msg, Throwable thrown) {
		this.logger.log(Level.FINE,ArgUtil.parseAsString(msg));
	}

	public void severe(Object msg, Throwable thrown) {
		this.logger.log(Level.SEVERE,ArgUtil.parseAsString(msg));
	}

	public void fatal(Object msg, Throwable thrown) {
		this.logger.log(Level.SEVERE,ArgUtil.parseAsString(msg));
	}

	public boolean isTraceEnabled() {
		return this.logger.isLoggable(Level.FINEST);
	}
}
