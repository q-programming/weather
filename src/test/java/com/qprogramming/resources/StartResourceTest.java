package com.qprogramming.resources;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Rule;
import org.junit.Test;

import io.dropwizard.testing.junit.ResourceTestRule;

public class StartResourceTest {

	private static final String TARGET_URL = "http://localhost:8080/start";
	@Rule
	public ResourceTestRule resource = ResourceTestRule.builder().addResource(new StartResource()).build();

	@Test
	public void testGetGreating() {
		Client client = resource.client();
		WebTarget startTarget = client.target(TARGET_URL);
		Invocation.Builder builder = startTarget.request(MediaType.TEXT_PLAIN);
		Response reposne = builder.get();
		assertEquals(Response.Status.OK, reposne.getStatusInfo());
		String actual = reposne.readEntity(String.class);
		assertEquals(StartResource.GREATING, actual);
	}

}
