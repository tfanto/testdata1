package com;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.ext.ContextResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class RestClient {

	private static Client client = null;

	private RestClient() throws JsonProcessingException {

	}

	public static Client getClient() {

		if (client == null) {
			client = ClientBuilder.newClient();
			client.register(new ContextResolver<ObjectMapper>() {
				@Override
				public ObjectMapper getContext(Class<?> type) {
					ObjectMapper mapper = new ObjectMapper();
					mapper.registerModule(new JavaTimeModule());
					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					return mapper;
				}
			});
		}

		return client;
	}

}
