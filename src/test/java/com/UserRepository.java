package com;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserRepository {

	private static final String REST_USER_END_POINT = "http://localhost:8080/server/rest/user";

	public List<User> findAll() {
		Client client = RestClient.getClient();
		Response response = client.target(REST_USER_END_POINT).request(MediaType.APPLICATION_JSON).get(Response.class);
		int status = response.getStatus();
		if (status == 200) {
			List<User> theList = response.readEntity(new GenericType<List<User>>() {
			});
			return theList;
		} else {
			return new ArrayList<>();
		}
	}

	public User findById(Long id) {
		return new User();
	}

	private User getById(Long id) {
		return new User();
	}

	public User save(User user) {

		ObjectMapper MAPPER = new ObjectMapper();
		try {
			String json = MAPPER.writeValueAsString(user);
			System.out.println(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Client client = RestClient.getClient();
		Response response = client.target(REST_USER_END_POINT).request(MediaType.APPLICATION_JSON)
				.post(Entity.json(user));
		int status = response.getStatus();
		if (status == 200) {
			User data = response.readEntity(new GenericType<User>() {
			});
			return data;
		} else {
			return null;
		}

	}

	public void delete(User user) {
	}

}
