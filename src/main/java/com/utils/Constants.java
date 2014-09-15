package com.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Constants {

	public static final Boolean defaultBoolean = Boolean.FALSE;
	public static final Integer defaultInteger = Integer.valueOf(0);
	public static final Long defaultLong = Long.valueOf(0L);
	public static final Double defaultDouble = Double.valueOf(0D);
	public static final String defaultString = "";
	public static final Object defaultObject = new Object();
	public static final Map<String, Object> emptyMap = Collections.unmodifiableMap(new HashMap<String, Object>());
	public static final List<Object> emptyList = Collections.unmodifiableList(new ArrayList<Object>());
	public static final List<List<Object>> emptyListOfList = Collections
			.unmodifiableList(new ArrayList<List<Object>>());
	public static final List<List<List<Object>>> emptyListOfListOfList = Collections
			.unmodifiableList(new ArrayList<List<List<Object>>>());
	public static final List<String> emptyStringList = Collections.unmodifiableList(new ArrayList<String>());
	public static final String CANCELLED_REQUEST = "04.03";

}
