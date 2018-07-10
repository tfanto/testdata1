package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UIRestserverTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(UIRestserverTests.class);

	private static final String REST_USER_END_POINT = "http://localhost:8080/server/rest/user";
	private static final String REST_ROLE_END_POINT = "http://localhost:8080/server/rest/role";

	private Random rnd = new Random();

	private static final int OK = 200;

	private static Client client;

	@BeforeClass
	public static void beforeClass() throws JsonProcessingException {

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

	
	@Test
	public void createTestData() throws JsonProcessingException {
		CreateTestDataService data = new CreateTestDataService();
		data.createTestData();
	}

	
	@Test
	public void getAllUsers() throws JsonProcessingException {
		List<User> rs = getAllUsersHelper();
		LOGGER.info("users : " + rs.size());
	}

	@Test
	public void getAllRoles() throws JsonProcessingException {
		List<Role> rs = getAllRolesHelper();
		LOGGER.info("roles : " + rs.size());
	}

	@Test
	public void getUser() throws JsonProcessingException {

		List<User> rs = getAllUsersHelper();
		int i = rnd.nextInt(rs.size());		
		Long id = rs.get(i).getId();

		Response response = client.target(REST_USER_END_POINT).path(String.valueOf(id))
				.request(MediaType.APPLICATION_JSON).get(Response.class);
		int status = response.getStatus();
		if (status == OK) {
			User ret = response.readEntity(new GenericType<User>() {
			});
			LOGGER.info(ret.toString());
		} else {
			LOGGER.info("status : " + status);
		}
	}

	@Test
	public void getRole() throws JsonProcessingException {

		List<Role> rs = getAllRolesHelper();
		int i = rnd.nextInt(rs.size());
		Long id = rs.get(i).getId();

		Response response = client.target(REST_ROLE_END_POINT).path(String.valueOf(id))
				.request(MediaType.APPLICATION_JSON).get(Response.class);
		int status = response.getStatus();
		if (status == OK) {
			Role ret = response.readEntity(new GenericType<Role>() {
			});
			LOGGER.info(ret.toString());
		} else {
			LOGGER.info("status : " + status);
		}
	}

	private List<Role> getAllRolesHelper() {

		Response response = client.target(REST_ROLE_END_POINT).request(MediaType.APPLICATION_JSON).get(Response.class);
		int status = response.getStatus();
		if (status == OK) {
			List<Role> theList = response.readEntity(new GenericType<List<Role>>() {
			});
			return theList;
		} else {
			return new ArrayList<>();
		}
	}

	private List<User> getAllUsersHelper() {

		Response response = client.target(REST_USER_END_POINT).request(MediaType.APPLICATION_JSON).get(Response.class);
		int status = response.getStatus();
		if (status == OK) {
			List<User> theList = response.readEntity(new GenericType<List<User>>() {
			});
			return theList;
		} else {
			return new ArrayList<>();
		}
	}

}
