package com.utils;

import java.util.Properties;

public final class DebugUtil {
	private static final boolean DEBUG_BUILD;
	private static final String DEBUG_BUILD_PROP = "debugBuild";
	private static final String DEBUG_BUILD_USERNAME = "username";
	private static final String DEBUG_BUILD_PASSWORD = "password";
	private static String userName;
	private static String passWord;
	private static Properties debugProperties;
	public static Properties getDebugProperties() {
		return debugProperties;
	}

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

	private DebugUtil() {
		throw new IllegalStateException(
				"This is a class with static methods and should not be instantiated");
	}

	public static boolean isDebugBuild() {
		return DEBUG_BUILD;
	}

	public static String getUserName() {
		return userName;
	}

	public static String getPassWord() {
		return passWord;
	}
}
