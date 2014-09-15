package com.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * JsonPath is to Json, what XPath is to XML. The main use of this class is
 * to navigate Json data structure to load/save/delete(map only) entries. Best
 * explained by taking an example to illustrate. </br>
 * 
 * <pre>
 * Consider the Json: {"key1":{"key2":["val1",{"key3":"val2"}]}}
 * 
 * JsonPath of "key1" => {"key2":["val1",{"key3":"val2"}]}
 * JsonPath of "key1/key2/[0]" => val1
 * JsonPath of "key1/key2/[1]/key3" => val2
 * </pre>
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@JsonSerialize(using = JsonPathSerializer.class)
public class JsonPath {

	private static final String VALID_TYPE = "valid_type";
	private static final String KEY = "key";

	interface PathElement {
		Object load(Object from);

		Object save(Object from, Object value);
	}

	private static class KeyAccess implements PathElement {
		private final String key;

		public KeyAccess(String key) {
			this.key = key;
		}

		@Override
		public Object load(Object from) {
			if (from instanceof Map) {
				return ((Map) from).get(this.key);
			}
			return null;
		}

		@Override
		public Object save(Object from, Object value) {
			if (from == null) {
				from = new LinkedHashMap<String, Object>();
			}
			if (from instanceof Map) {
				((Map) from).put(this.key, value);
				return from;
			}
			return null;
		}

		public void remove(Object from) {
			if (from instanceof Map) {
				((Map) from).remove(this.key);
			}
		}
	}

	private static class ArrayAccess implements PathElement {
		private final int index;

		public ArrayAccess(String key) {
			this.index = Integer.parseInt(key.substring(1, key.length() - 1));
		}

		public static boolean exists(String key) {
			return key.startsWith("[") && key.endsWith("]");
		}

		@Override
		public Object load(Object from) {
			if (from instanceof Object[]) {
				return ((Object[]) from)[this.index];
			}
			return CollectionUtil.getArray(((List<Object>) from), this.index);
		}

		@Override
		public Object save(Object from, Object value) {
			if (from == null) {
				from = new ArrayList<Object>();
			}
			if (from instanceof List) {
				CollectionUtil.putArray(((List) from), this.index, value);
				return from;
			}
			return null;
		}
	}

	private final String reference;
	private final ArrayList<PathElement> paths;

	public JsonPath(String reference) {

		this.reference = reference;
		this.paths = new ArrayList<PathElement>();
		StringTokenizer tokenizer = new StringTokenizer(reference, "/");
		while (tokenizer.hasMoreTokens()) {
			String tokenStr = tokenizer.nextToken();
			if (tokenStr == null || "".equals(tokenStr.trim())) {
				continue;
			}
			if (ArrayAccess.exists(tokenStr)) {
				this.paths.add(new ArrayAccess(tokenStr));
			} else {
				this.paths.add(new KeyAccess(tokenStr));
			}
		}
	}

	public static boolean exists(String value) {
		return value != null && value.startsWith("${") && value.endsWith("}");
	}

	public static JsonPath create(String reference) {
		return new JsonPath(reference.substring(2, reference.length() - 1));
	}

	public String getReference() {
		return this.reference;
	}

	// load and loadRequired method of T
	public <T> T load(Map<String, Object> properties, T defaultValue) {
		return loadInternal(properties, null, defaultValue, false);
	}

	/**
	 * Returns null if value is absent
	 * 
	 * @param properties
	 * @param defaultValue
	 * @return
	 */
	public <T> T loadNullable(Map<String, Object> properties, T defaultValue) {
		// T value = loadInternal(properties, null, defaultValue, false);
		// if (value == defaultValue) {
		// return null;
		// }
		// return value;
		Object value = internalLoad(properties, null);
		if (value == null) {
			return null;
		}

		return ArgUtil.parseAsT(value, defaultValue, false);

	}

	public <T> T loadRequired(Map<String, Object> properties, T defaultValue) {
		return loadInternal(properties, null, defaultValue, true);
	}

	public <T> T load(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue) {
		return loadInternal(properties, refProperties, defaultValue, false);
	}

	public <T> T loadNullable(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue) {
		// T value = loadInternal(properties, refProperties, defaultValue,
		// false);
		// if (value == defaultValue) {
		// return null;
		// }
		// return value;
		Object value = internalLoad(properties, refProperties);
		if (value == null) {
			return null;
		}

		return ArgUtil.parseAsT(value, defaultValue, false);
	}

	public <T> T loadRequired(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue) {
		return loadInternal(properties, refProperties, defaultValue, true);
	}

	public <T> T loadInternal(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue, boolean required) {
		try {
			return ArgUtil.parseAsT(internalLoad(properties, refProperties),
					defaultValue, required);
		} catch (ArgUtil.ParameterException e) {
			e.getData().put(KEY, this.reference);
			throw e;
		}
	}

	// load and loadRequired method of List<T>
	public <T> List<T> loadList(Map<String, Object> properties, T defaultValue) {
		return loadInternalList(properties, null, defaultValue,
				((List<T>) Constants.emptyList), false);
	}

	public <T> List<T> loadList(Map<String, Object> properties, T defaultValue,
			List<T> defaultListValue) {
		return loadInternalList(properties, null, defaultValue,
				defaultListValue, false);
	}

	public <T> List<T> loadListNullable(Map<String, Object> properties,
			T defaultValue) {
		List<T> value = loadInternalList(properties, null, defaultValue,
				((List<T>) Constants.emptyList), false);
		if (value == Constants.emptyList) {
			return null;
		}
		return value;
	}

	public <T> List<T> loadListNullable(Map<String, Object> properties,
			T defaultValue, List<T> defaultListValue) {
		List<T> value = loadInternalList(properties, null, defaultValue,
				defaultListValue, false);
		if (value == defaultListValue) {
			return null;
		}
		return value;
	}

	public <T> List<T> loadRequiredList(Map<String, Object> properties,
			T defaultValue) {
		return loadInternalList(properties, null, defaultValue,
				((List<T>) Constants.emptyList), true);
	}

	public <T> List<T> loadRequiredList(Map<String, Object> properties,
			T defaultValue, List<T> defaultListValue) {
		return loadInternalList(properties, null, defaultValue,
				defaultListValue, true);
	}

	public <T> List<T> loadList(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue) {
		return loadInternalList(properties, refProperties, defaultValue,
				((List<T>) Constants.emptyList), false);
	}

	public <T> List<T> loadList(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue,
			List<T> defaultListValue) {
		return loadInternalList(properties, refProperties, defaultValue,
				defaultListValue, false);
	}

	public <T> List<T> loadListNullable(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue) {
		List<T> value = loadInternalList(properties, refProperties,
				defaultValue, ((List<T>) Constants.emptyList), false);
		if (value == Constants.emptyList) {
			return null;
		}
		return value;
	}

	public <T> List<T> loadListNullable(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue,
			List<T> defaultListValue) {
		List<T> value = loadInternalList(properties, refProperties,
				defaultValue, defaultListValue, false);
		if (value == defaultListValue) {
			return null;
		}
		return value;
	}

	public <T> List<T> loadRequiredList(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue) {
		return loadInternalList(properties, refProperties, defaultValue,
				((List<T>) Constants.emptyList), true);
	}

	public <T> List<T> loadRequiredList(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue,
			List<T> defaultListValue) {
		return loadInternalList(properties, refProperties, defaultValue,
				defaultListValue, true);
	}

	public <T> List<T> loadInternalList(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue,
			List<T> defaultListValue, boolean required) {
		try {
			return ArgUtil.parseAsListOfT(
					internalLoad(properties, refProperties), defaultValue,
					defaultListValue, required);
		} catch (ArgUtil.ParameterException e) {
			e.getData().put(KEY, this.reference);
			e.getData().put(VALID_TYPE,
					"array[" + e.getData().get(VALID_TYPE) + "]");
			throw e;
		}
	}

	// load and loadRequired method of List<List<T>>
	public <T> List<List<T>> loadListOfList(Map<String, Object> properties,
			T defaultValue) {
		return loadInternalListOfList(properties, null, defaultValue,
				(List<T>) Constants.emptyList,
				(List<List<T>>) ((Object) Constants.emptyListOfList), false);
	}

	public <T> List<List<T>> loadListOfList(Map<String, Object> properties,
			T defaultValue, List<T> defaultListValue,
			List<List<T>> defaultListOfListValue) {
		return loadInternalListOfList(properties, null, defaultValue,
				defaultListValue, defaultListOfListValue, false);
	}

	public <T> List<List<T>> loadRequiredListOfList(
			Map<String, Object> properties, T defaultValue) {
		return loadInternalListOfList(properties, null, defaultValue,
				(List<T>) Constants.emptyList,
				(List<List<T>>) ((Object) Constants.emptyListOfList), true);
	}

	public <T> List<List<T>> loadRequiredListOfList(
			Map<String, Object> properties, T defaultValue,
			List<T> defaultListValue, List<List<T>> defaultListOfListValue) {
		return loadInternalListOfList(properties, null, defaultValue,
				defaultListValue, defaultListOfListValue, true);
	}

	public <T> List<List<T>> loadListOfList(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue) {
		return loadInternalListOfList(properties, refProperties, defaultValue,
				(List<T>) Constants.emptyList,
				(List<List<T>>) ((Object) Constants.emptyListOfList), false);
	}

	public <T> List<List<T>> loadListOfList(Map<String, Object> properties,
			Map<String, Object> refProperties, T defaultValue,
			List<T> defaultListValue, List<List<T>> defaultListOfListValue) {
		return loadInternalListOfList(properties, refProperties, defaultValue,
				defaultListValue, defaultListOfListValue, false);
	}

	public <T> List<List<T>> loadRequiredListOfList(
			Map<String, Object> properties, Map<String, Object> refProperties,
			T defaultValue) {
		return loadInternalListOfList(properties, refProperties, defaultValue,
				(List<T>) Constants.emptyList,
				(List<List<T>>) ((Object) Constants.emptyListOfList), true);
	}

	public <T> List<List<T>> loadRequiredListOfList(
			Map<String, Object> properties, Map<String, Object> refProperties,
			T defaultValue, List<T> defaultListValue,
			List<List<T>> defaultListOfListValue) {
		return loadInternalListOfList(properties, refProperties, defaultValue,
				defaultListValue, defaultListOfListValue, true);
	}

	public <T> List<List<T>> loadInternalListOfList(
			Map<String, Object> properties, Map<String, Object> refProperties,
			T defaultValue, List<T> defaultListValue,
			List<List<T>> defaultListOfListValue, boolean required) {
		try {
			return ArgUtil.parseAsListListOfT(
					internalLoad(properties, refProperties), defaultValue,
					defaultListValue, defaultListOfListValue, required);
		} catch (ArgUtil.ParameterException e) {
			e.getData().put(KEY, this.reference);
			e.getData().put(VALID_TYPE,
					"array[array[" + e.getData().get(VALID_TYPE) + "]]");
			throw e;
		}
	}

	/**
	 * actually follow the path in JsonPath and extract the value
	 * 
	 * @param properties
	 * @param refProperties
	 * @return Object
	 */
	private Object internalLoad(Map<String, Object> properties,
			Map<String, Object> refProperties) {
		Object current = properties;
		for (PathElement path : this.paths) {
			if (current == null) {
				break;
			}
			Object element = path.load(current);
			if (refProperties != null && element instanceof JsonPath) {
				element = ((JsonPath) element).load(refProperties, null);
			}
			current = element;
		}
		return current;
	}

	/**
	 * actually follow the path in JsonPath and save the value
	 * 
	 * @param properties
	 * @param value
	 */
	public void save(Map<String, Object> properties, Object value) {
		Object current = properties;
		int i = 0;
		for (; i < this.paths.size() - 1; ++i) {
			Object element = this.paths.get(i).load(current);
			if (element == null) {
				break;
			}
			current = element;
		}
		// we could only find path upto index i-1
		// we need now to create the path elements now in reverse
		Object insert = value;
		int j = this.paths.size() - 1;
		for (; j > i; --j) {
			insert = this.paths.get(j).save(null, insert);
		}
		this.paths.get(j).save(current, insert);
	}

	/**
	 * actually follow the path in JsonPath and remove (map only) the value
	 * 
	 * @param properties
	 */
	public void remove(Map<String, Object> properties) {
		Object current = properties;
		for (int i = 0; i < this.paths.size() - 1; ++i) {
			PathElement path = this.paths.get(i);
			if (current == null) {
				break;
			}
			current = path.load(current);
		}
		PathElement path = this.paths.get(this.paths.size() - 1);
		if (current != null && path instanceof KeyAccess) {
			((KeyAccess) path).remove(current);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.reference == null) ? 0 : this.reference.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		JsonPath other = (JsonPath) obj;
		if (this.reference == null) {
			if (other.reference != null) {
				return false;
			}
		} else if (!this.reference.equals(other.reference)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("${").append(this.reference)
				.append("}").toString();
	}
}

class JsonPathSerializer extends JsonSerializer<JsonPath> {
	@Override
	public void serialize(JsonPath ref, JsonGenerator jgen,
			SerializerProvider sp) throws IOException {
		jgen.writeString(ref.toString());
	}
}
