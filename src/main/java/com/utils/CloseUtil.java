package com.utils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;

public final class CloseUtil {

	private CloseUtil() {
		throw new IllegalStateException(
				"This is a utility class with static methods and should not be instantiated");
	}

	public static void close(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException e) {
			/* ignore */
		}
	}

	public static void close(Object hasCloseMethod) {
		try {
			Method method = hasCloseMethod.getClass().getMethod("close",
					new Class<?>[0]);
			method.invoke(hasCloseMethod, new Object[0]);
		} catch (Exception e) {
			/* ignore */
		}
	}
}
