package foodev.jsondiff.jsonwrap;

import java.util.Collection;
import java.util.Map.Entry;

/**
 * Common abstraction for json objects.
 * 
 * @since 1.0.0
 * @version @PROJECT_VERSION@
 */
public interface JzonObject extends JzonElement {

	/**
	 * Returns if this object has an element with the given key.
	 * 
	 * @param key
	 *            - the key to look for
	 * @return if this object has a key named liked that
	 */
	boolean has(String key);

	/**
	 * Adds an element with the given key.
	 * 
	 * @param key
	 *            - the key for the new element
	 * @return the new element
	 */
	void add(String key, JzonElement prop);

	/**
	 * Adds an integer with the given key.
	 * 
	 * @param key
	 *            - the key for the new integer
	 * @return the new integer
	 */
	void addProperty(String key, int prop);

	/**
	 * @return entry set with key / value pairs
	 */
	Collection<? extends Entry<String, JzonElement>> entrySet();

	/**
	 * Returns element at given key.
	 * 
	 * @param key
	 *            - key to look for
	 * @return the element at that key if any
	 */
	JzonElement get(String key);

	/**
	 * Removes an element with the given key.
	 * 
	 * @param key
	 *            - key to look for
	 */
	void remove(String key);

}
