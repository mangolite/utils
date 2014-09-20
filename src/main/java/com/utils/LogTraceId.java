package com.utils;

public class LogTraceId {
	private long traceId;
	private String message;

	public LogTraceId(long traceId, String message) {
		this.traceId = traceId;
		this.message = message;
	}

	public long getTraceId() {
		return this.traceId;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		return this.message;
	}

	/*
	 * STATIC FACTORY
	 */
	public static LogTraceId create(long traceId, String... messageParts) {
		StringBuilder sb = new StringBuilder(messageParts[0]);
		for (int i = 1; i < messageParts.length; ++i) {
			sb.append(messageParts[i]);
		}
		return new LogTraceId(traceId, sb.toString());
	}
}
