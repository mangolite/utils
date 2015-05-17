package org.spamjs.utils;

import java.util.concurrent.atomic.AtomicInteger;

// TODO: Auto-generated Javadoc
/**
 * The Class UniqueID.
 */
public final class UniqueID {
	
	/** The Constant INT_22. */
	private static final int INT_22 = 22;
	
	/** The atom. */
	private static AtomicInteger atom = new AtomicInteger();

	/**
	 * Instantiates a new unique id.
	 */
	private UniqueID() {
		throw new IllegalStateException("This is a class with static methods and should not be instantiated");
	}

	/**
	 * Generate.
	 *
	 * @return the long
	 */
	public static long generate() {
		return ((System.currentTimeMillis() << INT_22) | (((1 << INT_22) - 1) & atom.getAndIncrement()));
	}

	/**
	 * Generate string.
	 *
	 * @return : Unique String ID
	 */
	public static String generateString() {
		return Long.toString(generate(), 36);
	}
}
