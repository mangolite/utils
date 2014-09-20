package com.utils;

import java.util.regex.Pattern;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class LogLayout extends PatternLayout {
	private static final Pattern PATTERN = Pattern.compile("^", Pattern.MULTILINE);

	@Override
	public String format(LoggingEvent event) {
		String prefix = String.format("%16x ", Long.valueOf(getTraceId(event)));
		return PATTERN.matcher(super.format(event)).replaceAll(prefix);
	}

	private long getTraceId(LoggingEvent event) {
		Object message = event.getMessage();
		if (message instanceof LogTraceId) {
			return ((LogTraceId) message).getTraceId();
		}
		return ContextUtil.getTraceId(false);
	}
}
