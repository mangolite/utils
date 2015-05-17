package org.spamjs.utils;

import java.util.logging.Level;
import java.util.logging.Logger;


// TODO: Auto-generated Javadoc
/**
 * The Class Log.
 */
public class Log {

	/** The logger. */
	private Logger logger;

	/**
	 * Instantiates a new log.
	 */
	public Log() {
		StackTraceElement[] elements = new Throwable().getStackTrace();
		this.logger = Logger.getLogger(elements[1].getClassName());
	}

	/**
	 * Trace.
	 *
	 * @param msg the msg
	 */
	public void trace(Object msg) {
		this.logger.log(Level.FINEST, ArgUtil.parseAsString(msg));
	}

	/**
	 * Debug.
	 *
	 * @param msg the msg
	 */
	public void debug(Object msg) {
		this.logger.log(Level.FINER,ArgUtil.parseAsString(msg));
	}

	/**
	 * Info.
	 *
	 * @param msg the msg
	 */
	public void info(Object msg) {
		this.logger.log(Level.INFO,ArgUtil.parseAsString(msg));
	}

	/**
	 * Warn.
	 *
	 * @param msg the msg
	 */
	public void warn(Object msg) {
		this.logger.log(Level.WARNING,ArgUtil.parseAsString(msg));
	}

	/**
	 * Error.
	 *
	 * @param msg the msg
	 */
	public void error(Object msg) {
		this.logger.log(Level.FINE,ArgUtil.parseAsString(msg));
	}

	/**
	 * Severe.
	 *
	 * @param msg the msg
	 */
	public void severe(Object msg) {
		this.logger.log(Level.SEVERE,ArgUtil.parseAsString(msg));
	}

	/**
	 * Fatal.
	 *
	 * @param msg the msg
	 */
	public void fatal(Object msg) {
		this.logger.log(Level.SEVERE,ArgUtil.parseAsString(msg));
	}

	/**
	 * Trace.
	 *
	 * @param msg the msg
	 * @param thrown the thrown
	 */
	public void trace(Object msg, Throwable thrown) {
		this.logger.log(Level.FINEST, ArgUtil.parseAsString(msg),thrown);
	}

	/**
	 * Debug.
	 *
	 * @param msg the msg
	 * @param thrown the thrown
	 */
	public void debug(Object msg, Throwable thrown) {
		this.logger.log(Level.FINER, ArgUtil.parseAsString(msg),thrown);
	}

	/**
	 * Info.
	 *
	 * @param msg the msg
	 * @param thrown the thrown
	 */
	public void info(Object msg, Throwable thrown) {
		this.logger.log(Level.INFO,ArgUtil.parseAsString(msg));
	}

	/**
	 * Warn.
	 *
	 * @param msg the msg
	 * @param thrown the thrown
	 */
	public void warn(Object msg, Throwable thrown) {
		this.logger.log(Level.WARNING,ArgUtil.parseAsString(msg));
	}

	/**
	 * Error.
	 *
	 * @param msg the msg
	 * @param thrown the thrown
	 */
	public void error(Object msg, Throwable thrown) {
		this.logger.log(Level.FINE,ArgUtil.parseAsString(msg));
	}

	/**
	 * Severe.
	 *
	 * @param msg the msg
	 * @param thrown the thrown
	 */
	public void severe(Object msg, Throwable thrown) {
		this.logger.log(Level.SEVERE,ArgUtil.parseAsString(msg));
	}

	/**
	 * Fatal.
	 *
	 * @param msg the msg
	 * @param thrown the thrown
	 */
	public void fatal(Object msg, Throwable thrown) {
		this.logger.log(Level.SEVERE,ArgUtil.parseAsString(msg));
	}

	/**
	 * Checks if is trace enabled.
	 *
	 * @return true, if is trace enabled
	 */
	public boolean isTraceEnabled() {
		return this.logger.isLoggable(Level.FINEST);
	}
}
