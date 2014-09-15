package com.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

@SuppressWarnings("rawtypes")
public class JsonSerializerTypeSerializer extends JsonSerializer<JsonSerializerType> {
	@Override
	public void serialize(JsonSerializerType value, JsonGenerator jgen, SerializerProvider sp) throws IOException,
			JsonProcessingException {
		jgen.writeObject(value.toObject());
	}
}