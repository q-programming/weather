package com.qprogramming.weather.resources;

import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.qprogramming.weather.core.Meter;
import com.qprogramming.weather.core.Values;
import com.qprogramming.weather.db.MeterDAO;
import com.qprogramming.weather.db.ValuesDAO;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/meter")
@Produces(MediaType.APPLICATION_JSON)
public class MeterResource {

	private final MeterDAO meterDao;
	private final ValuesDAO valuesDao;

	public MeterResource(MeterDAO meterDAO, ValuesDAO vDao) {
		this.meterDao = meterDAO;
		this.valuesDao = vDao;
	}

	@Path("{meterId}")
	@GET
	@UnitOfWork
	public Meter getMeter(@PathParam("meterId") LongParam meterId) {
		return findSafely(meterId.get());
	}
	@Path("{meterId}/values")
	@GET
	@UnitOfWork
	public List<Values> getMeterMetrics(@PathParam("meterId") LongParam meterId) {
		return valuesDao.findByMeter(meterId.get());
	}

	@Path("/createDummy/{meterId}")
	@GET
	@UnitOfWork
	public Meter createValues(@PathParam("meterId") LongParam meterId) {
		Meter meter = findSafely(meterId.get());
		for (int i = 0; i < 10; i++) {
			Values value = new Values();
			value.setMeter(meter);
			value.setTemp(generate(10F, 15F));
			value.setHumidity(generate(40.23F, 60.5F));
			value.setPressure(generate(2000F, 2100F));
			value.setTimestamp(new DateTime().toDate());
			valuesDao.create(value);
		}
		return meterDao.create(meter);
	}

	private Meter findSafely(long personId) {
		final Optional<Meter> meter = meterDao.findById(personId);
		if (!meter.isPresent()) {
			throw new NotFoundException("Meter not found");
		}
		return meter.get();
	}

	
	
	private float generate(float min, float max) {
		return min + new Random().nextFloat() * (max - min);
	}

}
