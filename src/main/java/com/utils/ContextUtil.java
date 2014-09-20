package com.utils;

import java.util.HashMap;
import java.util.Map;

public final class ContextUtil {

	private ContextUtil() {
		throw new IllegalStateException(
				"This is a class with static methods and should not be instantiated");
	}

	public static final String TRACE_ID = "trace_id";
	public static final String TOKEN_ID = "token_id";
	public static final String USER_ID = "user_id";

	private static final ThreadLocal<Map<String, Object>> context = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}
	};

	public static Map<String, Object> get() {
		return context.get();
	}

	public static void set(Map<String, Object> map) {
		context.set(map);
	}

	public static void clear() {
		context.remove();
	}

	public static void setTraceId(long traceId) {
		context.get().put(TRACE_ID, Long.valueOf(traceId));
	}

	public static void setTraceId(String trace_id) {
		Long traceId = ArgUtil.parseAsLong(trace_id);
		if (traceId == null) {
			traceId = Long.valueOf(UniqueID.generate());
		}
		setTraceId(traceId.longValue());
	}

	public static long getTraceId() {
		return getTraceId(true);
	}

	public static long getTraceId(boolean generate) {
		Long traceId = (Long) context.get().get(TRACE_ID);
		if (traceId == null) {
			if (!generate) {
				return 0L;
			}
			traceId = Long.valueOf(UniqueID.generate());
			context.get().put(TRACE_ID, traceId);
		}
		return traceId.longValue();
	}

	public static Long getUserId() {
		return (Long) context.get().get(USER_ID);
	}

	public static void setUserId(long userId) {
		context.get().put(USER_ID, Long.valueOf(userId));
	}

}
