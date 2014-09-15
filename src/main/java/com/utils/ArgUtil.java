package com.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({ "unchecked", "rawtypes" })
/**
 * Utility function for parsing the objects as Integer, Long, Double, String, Boolean, Date
 * 
 */
public final class ArgUtil {

	private ArgUtil() {
		// Sonar code fix --> Utility classes should not have a public of
		// default constructor
		throw new IllegalStateException(
				"This is a class with static methods and should not be instantiated");
	}

	public static interface EnumById {
		/* must return uppercase letters */
		public String getId();
	}

	/**
	 * This method is called for generating the error message in case of
	 * Parameter missing or invalid exceptions
	 * 
	 * @param object
	 * @return : String indicating the type of the given object
	 */
	public static String getType(Object object) {
		if (object instanceof Date) {
			return "date";
		} else if (object instanceof String) {
			return "string";
		} else if (object instanceof Double) {
			return "double";
		} else if (object instanceof Long) {
			return "long";
		} else if (object instanceof Integer) {
			return "integer";
		} else if (object instanceof Boolean) {
			return "boolean";
		} else if (object == Constants.emptyMap) {
			return "object";
		}
		return "unknown";
	}

	public static String[] getTypeEnum(Enum enumValue) {
		ArrayList<String> list = new ArrayList<String>();
		for (Object element : enumValue.getClass().getEnumConstants()) {
			if (element instanceof EnumById) {
				list.add(((EnumById) element).getId().toLowerCase());
			} else {
				list.add(((Enum) element).name().toLowerCase());
			}
		}
		return list.toArray(new String[list.size()]);
	}

	@SuppressWarnings("serial")
	public static class ArgException extends RuntimeException {

		public static final String CODE = "code";
		public static final String DATA = "data";

		private static ThreadLocal<Set<String>> warnLocal = new ThreadLocal<Set<String>>() {
			@Override
			protected Set<String> initialValue() {
				return new HashSet<String>();
			}
		};

		private String errorCode;
		private Map<String, Object> data = new LinkedHashMap<String, Object>();

		public ArgException() {
		}

		public ArgException(String message) {
			super(message);
		}

		public ArgException(Throwable exp) {
			super(exp);
		}

		public String getErrorCode() {
			return this.errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public Map<String, Object> getData() {
			return this.data;
		}

		public Map<String, Object> errors() {
			Map<String, Object> innerMap = new LinkedHashMap<String, Object>();
			innerMap.put(CODE, this.errorCode);
			innerMap.put(DATA, this.data);
			return innerMap;
		}

		public static void warning(String message) {
			warnLocal.get().add(message);
		}

		public static List<String> warnings() {
			List<String> app = new ArrayList<String>(warnLocal.get());
			warnLocal.get().clear();
			return app;
		}

		@Override
		public String getMessage() {
			return JsonUtil.toJson(errors());
		}
	}

	@SuppressWarnings("serial")
	public static class ParameterException extends ArgException {
		protected boolean isMissing;

		public ParameterException(boolean isMissing) {
			this.isMissing = isMissing;
		}

		@Override
		public String getMessage() {
			StringBuilder sb = new StringBuilder();
			if (getData().get("key") != null) {
				sb.append(getData().get("key")).append(":");
			}
			sb.append(getMessageOverride());
			return sb.toString();
		}

		public String getMessageOverride() {
			return this.isMissing ? "parameter missing"
					: "parameter invalid - requires type "
							+ getData().get("valid_type");
		}
	}

	@SuppressWarnings("serial")
	public static class EnumParameterException extends ParameterException {
		private Enum enumValue;
		private String messageOverride;

		public EnumParameterException(boolean isMissing, Enum enumValue) {
			super(isMissing);
			this.enumValue = enumValue;
		}

		@Override
		public String getMessageOverride() {
			if (this.messageOverride == null) {
				StringBuilder sb = new StringBuilder("[");
				for (String element : getTypeEnum(this.enumValue)) {
					if (sb.length() > 1) {
						sb.append(",");
					}
					sb.append(element);
				}
				sb.append("]");

				this.messageOverride = this.isMissing ? "parameter missing"
						: "parameter invalid - requires one of "
								+ sb.toString();

			}
			return this.messageOverride;
		}
	}

	/**
	 * Parse as T
	 * 
	 */
	public static <T> T parseAsT(Object value, T defaultValue, boolean required) {
		if (value == null) {
			if (!required) {
				return defaultValue;
			}
			throw ArgExceptionFactory.paramMissingOrInvalid(null, null,
					defaultValue);
		}
		Object ret = value;
		if (defaultValue instanceof Boolean) {
			ret = parseAsBoolean(value);
		} else if (defaultValue instanceof Integer) {
			ret = parseAsInteger(value);
		} else if (defaultValue instanceof Long) {
			ret = parseAsLong(value);
		} else if (defaultValue instanceof Double) {
			ret = parseAsDouble(value);
		} else if (defaultValue instanceof String) {
			ret = parseAsString(value);
		} else if (defaultValue instanceof Enum) {
			ret = parseAsEnum(value, (Enum) defaultValue);
		}
		if (ret == null) {
			if (!required) {
				return defaultValue;
			}
			throw ArgExceptionFactory.paramMissingOrInvalid(null, value,
					defaultValue);
		}
		return (T) ret;
	}

	/**
	 * Parse as List<T>
	 * */
	public static <T> List<T> parseAsListOfT(Object value, T defaultValue,
			List<T> defaultListValue, boolean required) {
		if (value == null) {
			if (!required) {
				return defaultListValue;
			}
			throw ArgExceptionFactory.paramMissingOrInvalid(null, null,
					defaultValue);

		}
		if (value instanceof Object[]) {
			List<Object> list = new ArrayList<Object>();
			CollectionUtil.addAll(list, value);
			value = list;
		}
		if (value instanceof List) {
			List<T> list = (List<T>) value;
			for (int i = 0; i < list.size(); i++) {
				list.set(i, parseAsT(list.get(i), defaultValue, required));
			}
			return list;
		}
		throw ArgExceptionFactory.paramMissingOrInvalid(null, value,
				defaultValue);
	}

	/**
	 * Parse as List<List<T>>
	 * */
	public static <T> List<List<T>> parseAsListListOfT(Object value,
			T defaultValue, List<T> defaultListValue,
			List<List<T>> defaultListOfListValue, boolean required) {
		if (value == null) {
			if (!required) {
				return defaultListOfListValue;
			}
			throw ArgExceptionFactory.paramMissingOrInvalid(null, null,
					defaultValue);
		}
		if (value instanceof List) {
			List<List<T>> list = (List<List<T>>) value;
			for (int i = 0; i < list.size(); i++) {
				list.set(
						i,
						parseAsListOfT(list.get(i), defaultValue,
								defaultListValue, required));
			}
			return list;
		}
		throw ArgExceptionFactory.paramMissingOrInvalid(null, value,
				defaultValue);
	}

	/**
	 * Parse as List<List<List<T>>>
	 * */
	public static <T> List<List<List<T>>> parseAsListListListOfT(Object value,
			T defaultValue, List<T> defaultListValue,
			List<List<T>> defaultListListValue,
			List<List<List<T>>> defaultListListOfListValue, boolean required) {
		if (value == null) {
			if (!required) {
				return defaultListListOfListValue;
			}
			throw ArgExceptionFactory.paramMissingOrInvalid(null, null,
					defaultValue);
		}
		if (value instanceof List) {
			List<List<List<T>>> list = (List<List<List<T>>>) value;
			for (int i = 0; i < list.size(); i++) {
				list.set(
						i,
						parseAsListListOfT(list.get(i), defaultValue,
								defaultListValue, defaultListListValue,
								required));
			}
			return list;
		}
		throw ArgExceptionFactory.paramMissingOrInvalid(null, value,
				defaultValue);
	}

	/**
	 * <pre>
	 * Parses the given object as a Boolean
	 * 
	 * Formats Supported -
	 * 1) java.lang.Boolean
	 * 2) java.lang.Number (0 == Boolean.FALSE, rest all Boolean.TRUE) 
	 * 3) String ("true" / "false")
	 * </pre>
	 * 
	 * @param value
	 * @return : Boolean object if valid else null
	 */
	public static Boolean parseAsBoolean(Object value) {
		if (value instanceof Boolean) {
			return ((Boolean) value);
		} else if (value instanceof Number) {
			return Boolean.valueOf(((Number) value).intValue() != 0);
		} else if (value instanceof String) {
			return Boolean.valueOf(((String) value).equalsIgnoreCase("true"));
		}
		return null;
	}

	public static Boolean parseAsBoolean(Object value, Boolean nullValue) {
		if (value == null) {
			return nullValue;
		}
		return parseAsBoolean(value);
	}

	/**
	 * <pre>
	 * Parses the given object as an Integer
	 * 
	 * Formats Supported -
	 * 1) java.lang.Integer
	 * 2) java.lang.Number 
	 * 3) String ("1" / "2" etc.)
	 * 4) String ("0x1231" / "0x1a3e" etc.) - Hexadecimal or base 16 if starts with 0x
	 * 5) String ("023567" / "011256" etc.) - Octal or base 8 if starts with 0
	 * </pre>
	 * 
	 * @param value
	 * @return : Integer object if valid else null
	 */
	public static Integer parseAsInteger(Object value) {
		if (value instanceof Integer) {
			return ((Integer) value);
		} else if (value instanceof Number) {
			return Integer.valueOf(((Number) value).intValue());
		} else if (value instanceof String) {
			try {
				if (((String) value).startsWith("0x")) {
					return Integer.valueOf(Integer.parseInt(
							((String) value).substring(2), 16));
				} else if (((String) value).startsWith("0")
						&& ((String) value).length() > 1) {
					return Integer.valueOf(Integer.parseInt(
							((String) value).substring(1), 8));
				} else {
					return Integer.valueOf(Integer.parseInt((String) value));
				}
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * Parses the given object as an Long
	 * 
	 * Formats Supported -
	 * 1) java.lang.Long
	 * 2) java.lang.Number 
	 * 3) String ("1" / "2" etc.)
	 * 4) String ("0x1231" / "0x1a3e" etc.) - Hexadecimal or base 16 if starts with 0x
	 * 5) String ("023567" / "011256" etc.) - Octal or base 8 if starts with 0
	 * </pre>
	 * 
	 * @param value
	 * @return : Long object if valid else null
	 */
	public static Long parseAsLong(Object value) {
		if (value instanceof Long) {
			return ((Long) value);
		} else if (value instanceof Number) {
			return Long.valueOf(((Number) value).longValue());
		} else if (value instanceof String) {
			try {
				if (((String) value).startsWith("0x")) {
					return Long.valueOf(Long.parseLong(
							((String) value).substring(2), 16));
				} else if (((String) value).startsWith("0")
						&& ((String) value).length() > 1) {
					return Long.valueOf(Long.parseLong(
							((String) value).substring(1), 8));
				} else {
					return Long.valueOf(Long.parseLong((String) value));
				}
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * Parses the given object as an Double
	 * 
	 * Formats Supported -
	 * 1) java.lang.Double
	 * 2) java.lang.Number 
	 * 3) String ("1" / "2" etc.)
	 * </pre>
	 * 
	 * @param value
	 * @return : Double object if valid else null
	 */

	public static Double parseAsDouble(Object value) {
		if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof Number) {
			return Double.valueOf(((Number) value).doubleValue());
		} else if (value instanceof String) {
			try {
				return Double.valueOf(Double.parseDouble((String) value));
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}


	/**
	 * <pre>
	 * Parses the given object as date. It keeps the time information intact.
	 * 
	 * Formats Supported 
	 * 1) java.util.Date
	 * 2) unix timestamp
	 * </pre>
	 * 
	 * @param value
	 * @return : Date object if valid else null
	 */
	public static Date parseAsSimpleDate(Object value) {
		if (value instanceof Date) {
			return (Date) value;
		} else if (value instanceof Number) {
			return new Date(((Number) value).longValue());
		}
		return null;
	}

	/**
	 * Returns object.toString() if the object is not null else returns null
	 * 
	 * @param object
	 * @return : Returns object.toString() if the object is not null else
	 *         returns null
	 */
	public static String parseAsString(Object object) {
		if (object != null) {
			return object.toString();
		}
		return null;
	}

	/**
	 * @param object
	 * @param defaultValue
	 *            - if passed value is null or empty then default is returned.
	 * @return
	 */
	public static String parseAsString(Object object, String defaultValue) {
		if (object == null || Constants.defaultString.equals(object)) {
			return defaultValue;
		}
		return parseAsString(object);
	}

	public static Enum parseAsEnum(Object value, Enum defaultValue) {
		String enumString = parseAsString(value);
		if (enumString == null) {
			return null;
		}
		enumString = enumString.toUpperCase();
		try {
			if (defaultValue instanceof EnumById) {
				for (Object enumValue : defaultValue.getClass()
						.getEnumConstants()) {
					if (enumString.equals(((EnumById) enumValue).getId())) {
						return (Enum) enumValue;
					}
				}
				throw new IllegalArgumentException();
			}
			return Enum.valueOf(defaultValue.getClass(), enumString);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
