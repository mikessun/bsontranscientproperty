package com.michaelssun;

import java.io.IOException;
import java.util.Iterator;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.michaelssun.annotation.TransientProperty;
import com.michaelssun.util.ObjectMapperUtil;

/**
 * unit test for transient property persistence
 * 
 * @author michaelsun
 *
 */

public class TransientPropertyTest {

	private static final String AGENT = "agent";
	private static final String ACTION = "action1";
	static TransientPropertyBean value = null;

	@Before
	public void before() {
		if (value == null) {
			value = new TransientPropertyBean("smith", "google", ACTION);
		}
	}

	@Test
	public void testTransientProperty() throws JsonGenerationException, JsonMappingException, IOException {
		value.setAction(ACTION);
		JSONObject jsonObject = ObjectMapperUtil.toJsonObject(ObjectMapperUtil.DEFAULT_TRANSIENT_PROPERTY_OBJECT_MAPPER, value);
		Assert.assertTrue("should be " + jsonObject, jsonObject.get("name") != null);
		Assert.assertTrue("should be " + jsonObject, jsonObject.get(AGENT) != null);
		Assert.assertTrue("should not be " + jsonObject.get("action"), jsonObject.get("action") != null);
		for (@SuppressWarnings("unchecked")
		Iterator<String> iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
			String next = iterator.next();
			Assert.assertTrue("should be " + next, !"localcompany".equals(next));
		}
	}
	
	@Test
	public void testDefaultProperty() throws JsonGenerationException, JsonMappingException, IOException {
		value.setAction(ACTION);
		JSONObject jsonObject = ObjectMapperUtil.toJsonObject(ObjectMapperUtil.DEFAULT_TRANSIENT_PROPERTY_OBJECT_MAPPER, value);
		jsonObject = ObjectMapperUtil.toJsonObject(ObjectMapperUtil.DEFAULT_OBJECT_MAPPER, value);
		Assert.assertTrue("should be " + jsonObject, jsonObject.get("name") != null);
		Assert.assertTrue("should be " + jsonObject, jsonObject.get(AGENT) != null);
		Assert.assertTrue("should not be " + jsonObject.get("localcompany"), jsonObject.get("localcompany") != null);
		Assert.assertTrue("should not be " + jsonObject.get("action"), jsonObject.get("action") != null);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	class TransientPropertyBean {
		@JsonProperty("name")
		private String name;
		@JsonProperty("localcompany")
		@TransientProperty
		private String company;
		@JsonProperty("action")
		private String action;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public TransientPropertyBean(String name, String company, String action) {
			this.name = name;
			this.company = company;
			this.action = action;
		}
	}
}
