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

public class RoleRepository {

	private static final String REST_ROLE_END_POINT = "http://localhost:8080/server/rest/role";
	
	public List<Role> findAll() {

		Client client = RestClient.getClient();
		Response response = client.target(REST_ROLE_END_POINT).request(MediaType.APPLICATION_JSON).get(Response.class);
		int status = response.getStatus();
		if (status == 200) {
			List<Role> theList = response.readEntity(new GenericType<List<Role>>() {
			});
			return theList;
		} else {
			return new ArrayList<>();
		}
	}

	public Role save(Role role) {
		
		ObjectMapper MAPPER = new ObjectMapper();
		try {
			String json = MAPPER.writeValueAsString(role);
			System.out.println(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Client client = RestClient.getClient();
		Response response = client.target(REST_ROLE_END_POINT).request(MediaType.APPLICATION_JSON)
				.post(Entity.json(role));
		int status = response.getStatus();
		if (status == 200) {
			Role data = response.readEntity(new GenericType<Role>() {
			});
			return data;
		} else {
			return null;
		}

	}

}
