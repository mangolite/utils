package org.spamjs.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class StringUtil.
 */
public final class StringUtil {

	/**
	 * Instantiates a new string util.
	 */
	private StringUtil() {
		throw new IllegalStateException("This is a class with static methods and should not be instantiated");
	}

	// return the longest common prefix of s and t
	/**
	 * Lcp.
	 *
	 * @param s the s
	 * @param t the t
	 * @return the string
	 */
	public static String lcp(String s, String t) {
		int n = Math.min(s.length(), t.length());
		for (int i = 0; i < n; i++) {
			if (s.charAt(i) != t.charAt(i)) {
				return s.substring(0, i);
			}
		}
		return s.substring(0, n);
	}

	// return if s is null or empty
	/**
	 * Checks if is empty.
	 *
	 * @param s the s
	 * @return true, if is empty
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s.trim());
	}

	/**
	 * Join.
	 *
	 * @param array the array
	 * @param sep the sep
	 * @return the string
	 */
	public static String join(String[] array, String sep) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (int i = 0; i < array.length; ++i) {
			String element = array[i];
			if (element == null) {
				continue;
			}
			if (!first) {
				sb.append(sep);
			}
			first = false;
			sb.append(element);
		}
		return sb.toString();
	}

	/**
	 * Join.
	 *
	 * @param col the col
	 * @param sep the sep
	 * @return the string
	 */
	public static String join(Collection<String> col, String sep) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		Iterator<String> colIt = col.iterator();
		while (colIt.hasNext()) {
			String element = colIt.next();
			if (element == null) {
				continue;
			}
			if (!first) {
				sb.append(sep);
			}
			first = false;
			sb.append(element);
		}
		return sb.toString();
	}

	/**
	 * This method returns a string with comma separated objects' string value.
	 * Note this returns an extra comma in the end
	 *
	 * @param objects the objects
	 * @return the string
	 */
	public static String commaSeparated(Object... objects) {
		// Fix for Sonar: Performance - Method concatenates strings using + in a
		// loop
		StringBuilder sb = new StringBuilder();
		for (Object obj : objects) {
			sb.append(obj);
			sb.append(",");
		}
		return (sb.toString().length() > 0) ? sb.toString().substring(0, sb.toString().length() - 1) : sb.toString();
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String,
	 * handling <code>null</code> by returning <code>null</code>.
	 * </p>
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use {@link #strip(String)}.
	 * </p>
	 * 
	 * <p>
	 * To trim your choice of characters, use the {@link #strip(String, String)}
	 * methods.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.trim(null)          = null
	 * StringUtils.trim("")            = ""
	 * StringUtils.trim("     ")       = ""
	 * StringUtils.trim("abc")         = "abc"
	 * StringUtils.trim("    abc    ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed string, <code>null</code> if null String input
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * Converts string into title case.
	 * 
	 * @param str
	 *            the string to be converted into title case.
	 * @return input string in title case. If null or empty string is received
	 *         in input, the function returns the input as it is.
	 */
	public static String toTitleCase(String str) {
		if (str == null) {
			return str;
		} else {
			str = str.trim();
			if (str == "") {
				return str;
			}
		}
		StringBuilder result = new StringBuilder();
		String[] splittedStr = str.split(" ");
		for (int i = 0; i < splittedStr.length; i++) {
			String tempStr = splittedStr[i];
			result.append(tempStr.substring(0, 1).toUpperCase()).append(tempStr.substring(1).toLowerCase()).append(" ");
		}
		return result.toString().trim();
	}

	/**
	 * Does not take into account spaces and special chars etc word separators.
	 *
	 * @param str the str
	 * @return the string
	 */
	/*
	 * public static String camelCaseWord(String str) { return str.substring(0,
	 * 1).toUpperCase() + str.substring(1).toLowerCase(); }
	 */

	public static String camelCaseWord(String str) {
		return str == null ? null : (str.length() > 1 ? str.substring(0, 1).toUpperCase()
				+ str.substring(1).toLowerCase() : str.substring(0, 1).toUpperCase());
	}

	/**
	 * Encode.
	 *
	 * @param decoded the decoded
	 * @return : UTF-8 url encoded string for sent arg
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public static String encode(String decoded) throws UnsupportedEncodingException {
		return URLEncoder.encode(decoded, "UTF-8");
	}

	/**
	 * Decode.
	 *
	 * @param encoded the encoded
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public static String decode(String encoded) throws UnsupportedEncodingException {
		return URLDecoder.decode(encoded, "UTF-8");
	}

}
