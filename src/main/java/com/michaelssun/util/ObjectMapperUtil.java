package com.michaelssun.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.Module;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.TransientPropertyAnnotationIntrospector;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * an object mapper utility class 
 */

public class ObjectMapperUtil {
	public static final ObjectMapper DEFAULT_TRANSIENT_PROPERTY_OBJECT_MAPPER = getDefaultTranscientPropertyObjectMapper();
	public static final ObjectMapper DEFAULT_OBJECT_MAPPER = getDefaultObjectMapper();

	private static ObjectMapper getDefaultTranscientPropertyObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.registerModule(new Module() {

			@Override
			public String getModuleName() {
				return "Transient Property Bson Module";
			}

			@Override
			public void setupModule(SetupContext arg0) {
			}

			@Override
			public Version version() {
				return new Version(1, 0, 0, "");
			}

		});

		AnnotationIntrospector ai = new TransientPropertyAnnotationIntrospector();
		DeserializationConfig dconfig = objectMapper.getDeserializationConfig().withAnnotationIntrospector(ai);
		SerializationConfig sconfig = objectMapper.getSerializationConfig().withAnnotationIntrospector(ai);
		objectMapper.setDeserializationConfig(dconfig);
		objectMapper.setSerializationConfig(sconfig);

		return objectMapper;
	}
	
	private static ObjectMapper getDefaultObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.registerModule(new Module() {

			@Override
			public String getModuleName() {
				return "Transient Property Bson Module";
			}

			@Override
			public void setupModule(SetupContext arg0) {
			}

			@Override
			public Version version() {
				return new Version(1, 0, 0, "");
			}

		});

		return objectMapper;
	}

	/**
	 * Helper method to convert model object to a DBObject.
	 * 
	 * @param objectMapper
	 * @param object
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws ObjectMapperException
	 */
	public static JSONObject toJsonObject(ObjectMapper objectMapper, Object object) throws JsonGenerationException, JsonMappingException,
			JSONException, IOException {
		return new JSONObject(objectMapper.writeValueAsString(object));
	}
}
