package com.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.utils.ArgUtil.EnumById;

public final class JsonUtil {

	private JsonUtil() {
		throw new IllegalStateException("This is a class with static methods and should not be instantiated");
	}

	public static class JsonUtilConfigurable {

		private final ObjectMapper mapper;

		/**
		 * @return the mapper
		 */
		public ObjectMapper getMapper() {
			return this.mapper;
		}

		public JsonUtilConfigurable(ObjectMapper mapper) {
			this.mapper = mapper;
		}

		public <E> E fromJson(String json, Class<E> type) {
			if (json == null || "".equals(json.trim()) || "\"\"".equals(json.trim())) {
				return null;
			}
			try {
				return getMapper().readValue(json, type);
			} catch (IOException e) {
				LOG.warn("error converting from json=" + json, e);
			}
			return null;
		}

		public String toJson(Object object) {
			try {
				return getMapper().writeValueAsString(object);
			} catch (IOException e) {
				LOG.warn("error converting to json", e);
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		public Map<String, Object> toMap(Object object) {
			return getMapper().convertValue(object, Map.class);
		}

		public void toJson(OutputStream outputStream, Object object) {
			try {
				getMapper().writeValue(outputStream, object);
			} catch (IOException e) {
				LOG.warn("error converting to json", e);
			}
		}
	}

	static final Log LOG = new Log();
	public static final JsonUtil.JsonUtilConfigurable instance;
	static {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("MyModule", new Version(1, 0, 0, null));
		module.addSerializer(EnumById.class, new EnumByIdSerializer());
		module.addSerializer(JsonSerializerType.class, new JsonSerializerTypeSerializer());
		mapper.registerModule(module);
		instance = new JsonUtil.JsonUtilConfigurable(mapper);
	}

	public static <E> E fromJson(String json, Class<E> type) {
		return instance.fromJson(json, type);
	}

	public static String toJson(Object object) {
		return instance.toJson(object);
	}

	public static Map<String, Object> toMap(Object object) {
		return instance.toMap(object);
	}

	public static void toJson(OutputStream outputStream, Object object) {
		instance.toJson(outputStream, object);
	}

	public static MapDifference<String, Object> getDiff(Map<String, Object> left, Map<String, Object> right) {
		return Maps.difference(left, right);
	}

	public static MapDifference<String, Object> getDiff(Object left, Object right) {
		return getDiff(instance.toMap(left), instance.toMap(right));
	}

}

class EnumByIdSerializer extends JsonSerializer<EnumById> {
	@Override
	public void serialize(EnumById value, JsonGenerator jgen, SerializerProvider sp) throws IOException,
			JsonProcessingException {
		jgen.writeString(value.getId());
	}
}
