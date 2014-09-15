package com.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class CollectionUtil {

	private CollectionUtil() {
		throw new IllegalStateException(
				"Class for static methods. Can not be instantiated");
	}

	public static <T> T getArray(List<T> list, int index) {
		if (list != null && list.size() > index) {
			return list.get(index);
		}
		return null;
	}

	public static <T> T getArray(T[] array, int index) {
		if (array != null && array.length > index) {
			return array[index];
		}
		return null;
	}

	public static <T> T getArray(List<T> list, int index, T defaultValue) {
		T value = getArray(list, index);
		return value != null ? value : defaultValue;
	}

	public static <T> T getArray(T[] array, int index, T defaultValue) {
		T value = getArray(array, index);
		return value != null ? value : defaultValue;
	}

	public static <T> T getArray(List<List<T>> list, int index1, int index2) {
		if (list != null && list.size() > index1) {
			List<T> list2 = list.get(index1);
			if (list2 != null && list2.size() > index2) {
				return list2.get(index2);
			}
		}
		return null;
	}

	public static <T extends Comparable<? super T>> List<T> asSortedList(
			Collection<T> c) {
		List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list);
		return list;
	}

	public static <T extends Comparable<? super T>> Set<T> asSortedSet(
			Collection<T> c) {
		List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list);
		Set<T> set = new LinkedHashSet<T>(list);
		// set.addAll(list);
		return set;
	}

	public static <E> void putArray(List<E> list, int index, E value) {
		if (list.size() <= index) {
			for (; list.size() <= index;) {
				list.add(null);
			}
		}
		list.set(index, value);
	}

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

	@SafeVarargs
	public static <T> List<T> getList(T... elements) {
		List<T> list = new ArrayList<T>();
		for (T element : elements) {
			list.add(element);
		}
		return list;
	}

	@SafeVarargs
	public static <T> Set<T> getSet(T... elements) {
		Set<T> set = new HashSet<T>();
		for (T element : elements) {
			set.add(element);
		}
		return set;
	}

	public static List<String> getList(String[] arrylist) {
		return new ArrayList<String>(Arrays.asList(arrylist));
	}
}
