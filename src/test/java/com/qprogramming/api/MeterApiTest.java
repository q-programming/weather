package com.qprogramming.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.base.Optional;
import com.qprogramming.weather.api.MeterApi;
import com.qprogramming.weather.core.Meter;
import com.qprogramming.weather.db.MeterDAO;
import com.qprogramming.weather.db.ValuesDAO;

import io.dropwizard.testing.junit.ResourceTestRule;

public class MeterApiTest {

	private static final String METER_URL = "http://localhost:8080/api/meter/1";
	private static final String VALUES_URL = "http://localhost:8080/api/meter/1/values";

	private static final String NAME = "Meter Name";

	private static final MeterDAO meterDaoMock = mock(MeterDAO.class);
	private static final ValuesDAO valuesDaoMock = mock(ValuesDAO.class);

	@Before
	public void setup() {
		Meter meter = new Meter();
		meter.setId(1L);
		meter.setMetername(NAME);
		when(meterDaoMock.findById(1L)).thenReturn(Optional.of(meter));
	}

	@Rule
	public ResourceTestRule resource = ResourceTestRule.builder().addResource(new MeterApi(meterDaoMock, valuesDaoMock))
			.build();

	@Test
	public void getMeterTest() {
		Client client = resource.client();
		WebTarget startTarget = client.target(METER_URL);
		Invocation.Builder builder = startTarget.request(MediaType.APPLICATION_JSON);
		Response reposne = builder.get();
		assertEquals(Response.Status.OK, reposne.getStatusInfo());
		String actual = reposne.readEntity(String.class);
		assertNotNull(actual);
	}
	@Test
	public void getMeterValuesTest() {
		Client client = resource.client();
		WebTarget startTarget = client.target(VALUES_URL);
		Invocation.Builder builder = startTarget.request(MediaType.APPLICATION_JSON);
		Response reposne = builder.get();
		assertEquals(Response.Status.OK, reposne.getStatusInfo());
		String actual = reposne.readEntity(String.class);
		assertNotNull(actual);
	}


}
