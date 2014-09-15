package com.utils;

import com.utils.ArgUtil.ArgException;
import com.utils.ArgUtil.EnumParameterException;
import com.utils.ArgUtil.ParameterException;

public final class ArgExceptionFactory {
	// generic types
	public static final String PARAMETER_MISSING = "01.001";
	public static final String PARAMETER_INVALID = "01.002";

	private ArgExceptionFactory() {
		throw new IllegalStateException(
				"This is a utility class with static methods and should not be instantiated");
	}

	// return Exception for missing or invalid parameter
	@SuppressWarnings("rawtypes")
	public static ArgException paramMissingOrInvalid(Object key, Object value,
			Object defaultValue) {
		ParameterException argException;
		if (defaultValue instanceof Enum) {
			argException = new EnumParameterException(value == null,
					(Enum) defaultValue);
			argException.getData().put("valid_val",
					ArgUtil.getTypeEnum((Enum) defaultValue));
		} else {
			argException = new ParameterException(value == null);
			argException.getData().put("valid_type",
					ArgUtil.getType(defaultValue));
		}
		if (value != null) {
			argException.getData().put("val", value);
			argException.setErrorCode(PARAMETER_INVALID);
		} else {
			argException.setErrorCode(PARAMETER_MISSING);
		}
		if (key != null) {
			argException.getData().put("key", key);
		}
		return argException;
	}
}
