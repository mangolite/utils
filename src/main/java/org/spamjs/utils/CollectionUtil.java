package org.spamjs.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class CollectionUtil.
 */
public final class CollectionUtil {

	/**
	 * Instantiates a new collection util.
	 */
	private CollectionUtil() {
		throw new IllegalStateException(
				"Class for static methods. Can not be instantiated");
	}

	/**
	 * Gets the array.
	 *
	 * @param <T> the generic type
	 * @param list the list
	 * @param index the index
	 * @return the array
	 */
	public static <T> T getArray(List<T> list, int index) {
		if (list != null && list.size() > index) {
			return list.get(index);
		}
		return null;
	}

	/**
	 * Gets the array.
	 *
	 * @param <T> the generic type
	 * @param array the array
	 * @param index the index
	 * @return the array
	 */
	public static <T> T getArray(T[] array, int index) {
		if (array != null && array.length > index) {
			return array[index];
		}
		return null;
	}

	/**
	 * Gets the array.
	 *
	 * @param <T> the generic type
	 * @param list the list
	 * @param index the index
	 * @param defaultValue the default value
	 * @return the array
	 */
	public static <T> T getArray(List<T> list, int index, T defaultValue) {
		T value = getArray(list, index);
		return value != null ? value : defaultValue;
	}

	/**
	 * Gets the array.
	 *
	 * @param <T> the generic type
	 * @param array the array
	 * @param index the index
	 * @param defaultValue the default value
	 * @return the array
	 */
	public static <T> T getArray(T[] array, int index, T defaultValue) {
		T value = getArray(array, index);
		return value != null ? value : defaultValue;
	}

	/**
	 * Gets the array.
	 *
	 * @param <T> the generic type
	 * @param list the list
	 * @param index1 the index1
	 * @param index2 the index2
	 * @return the array
	 */
	public static <T> T getArray(List<List<T>> list, int index1, int index2) {
		if (list != null && list.size() > index1) {
			List<T> list2 = list.get(index1);
			if (list2 != null && list2.size() > index2) {
				return list2.get(index2);
			}
		}
		return null;
	}

	/**
	 * As sorted list.
	 *
	 * @param <T> the generic type
	 * @param c the c
	 * @return the list
	 */
	public static <T extends Comparable<? super T>> List<T> asSortedList(
			Collection<T> c) {
		List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list);
		return list;
	}

	/**
	 * As sorted set.
	 *
	 * @param <T> the generic type
	 * @param c the c
	 * @return the sets the
	 */
	public static <T extends Comparable<? super T>> Set<T> asSortedSet(
			Collection<T> c) {
		List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list);
		Set<T> set = new LinkedHashSet<T>(list);
		// set.addAll(list);
		return set;
	}

	/**
	 * Put array.
	 *
	 * @param <E> the element type
	 * @param list the list
	 * @param index the index
	 * @param value the value
	 */
	public static <E> void putArray(List<E> list, int index, E value) {
		if (list.size() <= index) {
			for (; list.size() <= index;) {
				list.add(null);
			}
		}
		list.set(index, value);
	}

	/**
	 * Exists.
	 *
	 * @param value the value
	 * @param collection the collection
	 * @return true, if successful
	 */
	public static boolean exists(String value, String[] collection) {
		if (value == null || collection == null) {
			return false;
		}
		for (String element : collection) {
			if (value.equals(element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Exists.
	 *
	 * @param <T> the generic type
	 * @param value the value
	 * @param collection the collection
	 * @return true, if successful
	 */
	public static <T> boolean exists(T value, Collection<T> collection) {
		if (value == null || collection == null) {
			return false;
		}
		for (T element : collection) {
			if (value.equals(element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds the all.
	 *
	 * @param collection the collection
	 * @param value the value
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addAll(Collection collection, Object value) {
		if (value instanceof Collection) {
			collection.addAll((Collection) value);
		} else if (value instanceof Object[]) {
			for (Object element : (Object[]) value) {
				collection.add(element);
			}
		}
	}

	/**
	 * Gets the list.
	 *
	 * @param <T> the generic type
	 * @param elements the elements
	 * @return the list
	 */
	@SafeVarargs
	public static <T> List<T> getList(T... elements) {
		List<T> list = new ArrayList<T>();
		for (T element : elements) {
			list.add(element);
		}
		return list;
	}

	/**
	 * Gets the sets the.
	 *
	 * @param <T> the generic type
	 * @param elements the elements
	 * @return the sets the
	 */
	@SafeVarargs
	public static <T> Set<T> getSet(T... elements) {
		Set<T> set = new HashSet<T>();
		for (T element : elements) {
			set.add(element);
		}
		return set;
	}

	/**
	 * Gets the list.
	 *
	 * @param arrylist the arrylist
	 * @return the list
	 */
	public static List<String> getList(String[] arrylist) {
		return new ArrayList<String>(Arrays.asList(arrylist));
	}
}
