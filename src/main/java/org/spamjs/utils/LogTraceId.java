package org.spamjs.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class LogTraceId.
 */
public class LogTraceId {
	
	/** The trace id. */
	private long traceId;
	
	/** The message. */
	private String message;

	/**
	 * Instantiates a new log trace id.
	 *
	 * @param traceId the trace id
	 * @param message the message
	 */
	public LogTraceId(long traceId, String message) {
		this.traceId = traceId;
		this.message = message;
	}

	/**
	 * Gets the trace id.
	 *
	 * @return the trace id
	 */
	public long getTraceId() {
		return this.traceId;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.message;
	}

	/*
	 * STATIC FACTORY
	 */
	/**
	 * Creates the.
	 *
	 * @param traceId the trace id
	 * @param messageParts the message parts
	 * @return the log trace id
	 */
	public static LogTraceId create(long traceId, String... messageParts) {
		StringBuilder sb = new StringBuilder(messageParts[0]);
		for (int i = 1; i < messageParts.length; ++i) {
			sb.append(messageParts[i]);
		}
		return new LogTraceId(traceId, sb.toString());
	}
}
