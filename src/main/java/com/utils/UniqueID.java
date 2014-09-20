package com.utils;

import java.util.concurrent.atomic.AtomicInteger;

public final class UniqueID {
	private static final int INT_22 = 22;
	private static AtomicInteger atom = new AtomicInteger();

	private UniqueID() {
		// Sonar code fix --> Utility classes should not have a public of
		// default constructor
		throw new IllegalStateException("This is a class with static methods and should not be instantiated");
	}

	public static long generate() {
		return ((System.currentTimeMillis() << INT_22) | (((1 << INT_22) - 1) & atom.getAndIncrement()));
	}

	/**
	 * @return : Unique String ID
	 */
	public static String generateString() {
		return Long.toString(generate(), 36);
	}
}
