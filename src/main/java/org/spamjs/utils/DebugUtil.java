package org.spamjs.utils;

import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class DebugUtil.
 */
public final class DebugUtil {
	
	/** The Constant DEBUG_BUILD. */
	private static final boolean DEBUG_BUILD;
	
	/** The Constant DEBUG_BUILD_PROP. */
	private static final String DEBUG_BUILD_PROP = "debugBuild";
	
	/** The Constant DEBUG_BUILD_USERNAME. */
	private static final String DEBUG_BUILD_USERNAME = "username";
	
	/** The Constant DEBUG_BUILD_PASSWORD. */
	private static final String DEBUG_BUILD_PASSWORD = "password";
	
	/** The user name. */
	private static String userName;
	
	/** The pass word. */
	private static String passWord;
	
	/** The debug properties. */
	private static Properties debugProperties;
	
	/**
	 * Gets the debug properties.
	 *
	 * @return the debug properties
	 */
	public static Properties getDebugProperties() {
		return debugProperties;
	}

	/**
	 * Sets the debug properties.
	 *
	 * @param debugProperties the new debug properties
	 */
	public static void setDebugProperties(Properties debugProperties) {
		DebugUtil.debugProperties = debugProperties;
	}

	static {
		boolean temporaryDebugBuild = false;
		String tempUserName = "";
		String tempPassWord = "";
		try {
			debugProperties = new Properties();
			debugProperties.load(DebugUtil.class
					.getResourceAsStream("/debug.properties"));
			temporaryDebugBuild = "true".equals(debugProperties
					.get(DEBUG_BUILD_PROP));

			if (temporaryDebugBuild) {
				if (debugProperties.containsKey(DEBUG_BUILD_USERNAME)) {
					tempUserName = (String) debugProperties
							.get(DEBUG_BUILD_USERNAME);
				}
				if (debugProperties.containsKey(DEBUG_BUILD_PASSWORD)) {
					tempPassWord = (String) debugProperties
							.get(DEBUG_BUILD_PASSWORD);
				}
			}

		} catch (Throwable e) {
		}
		DEBUG_BUILD = temporaryDebugBuild;
		userName = tempUserName;
		passWord = tempPassWord;
	}

	/**
	 * Instantiates a new debug util.
	 */
	private DebugUtil() {
		throw new IllegalStateException(
				"This is a class with static methods and should not be instantiated");
	}

	/**
	 * Checks if is debug build.
	 *
	 * @return true, if is debug build
	 */
	public static boolean isDebugBuild() {
		return DEBUG_BUILD;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public static String getUserName() {
		return userName;
	}

	/**
	 * Gets the pass word.
	 *
	 * @return the pass word
	 */
	public static String getPassWord() {
		return passWord;
	}
}
