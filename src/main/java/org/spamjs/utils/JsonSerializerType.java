package org.spamjs.utils;

public interface JsonSerializerType<T> {
	/**
	 * Create the object value from object of type T
	 */
	Object toObject();
}
