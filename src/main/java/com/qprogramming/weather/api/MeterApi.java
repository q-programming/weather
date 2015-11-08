/**
 * REST api for accessing meter and it's value
 *  @author Jakub Romaniszun
 *  @date 01.11.2019
 */
package com.qprogramming.weather.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.qprogramming.weather.core.Meter;
import com.qprogramming.weather.core.Values;
import com.qprogramming.weather.core.sorters.ValuesSorter;
import com.qprogramming.weather.db.MeterDAO;
import com.qprogramming.weather.db.ValuesDAO;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/api/meter")
@Produces(MediaType.APPLICATION_JSON)
public class MeterApi {

	private final MeterDAO meterDao;
	private final ValuesDAO valuesDao;

	public MeterApi(MeterDAO meterDao, ValuesDAO valuesDao) {
		this.meterDao = meterDao;
		this.valuesDao = valuesDao;
	}

	/**
	 * Get json object of meter
	 * 
	 * @param meterId
	 * @return
	 */
	@Path("{meterId}")
	@GET
	@UnitOfWork
	public Meter getMeter(@PathParam("meterId") LongParam meterId) {
		return findSafely(meterId.get());
	}

	/**
	 * Returns all values from meter with given meterId
	 * 
	 * @param meterId
	 * @return
	 */
	@Path("{meterId}/values")
	@GET
	@UnitOfWork
	public MeterData getMeterData(@PathParam("meterId") LongParam meterId, @QueryParam("from") String date_from,
			@QueryParam("to") String date_to) {
		List<Values> result = getValues(meterId, date_from, date_to);
		/// TODO remove after random data eliminated
		result = eliminateDuplicates(result);
		MeterData data = new MeterData(result.size());
		for (int i = 0; i < result.size(); i++) {
			Values value = result.get(i);
			System.out.println(value.getTimestamp().getMillis() + ";" + value.getTemp() + ";" + value.getHumidity()
					+ ";" + value.getPressure());
			data.setHumidity(i, value.getTimestamp().getMillis(), roundFloatDecimals(value.getHumidity()));
			data.setTemperature(i, value.getTimestamp().getMillis(), roundFloatDecimals(value.getTemp()));
			data.setPressure(i, value.getTimestamp().getMillis(), roundFloatDecimals(value.getPressure()));
		}
		return data;
	}

	private List<Values> eliminateDuplicates(List<Values> result) {
		List<Values> clean = new ArrayList<Values>();
		Set<String> lookup = new HashSet<String>();
		for (Values value : result) {
			if (lookup.add(value.getDate())) {
				clean.add(value);
			}
		}
		Collections.sort(clean, new ValuesSorter(false));
		return clean;
	}

	private double roundFloatDecimals(Float number) {
		return (double) Math.round(number * 100) / 100;
	}

	private List<Values> getValues(LongParam meterId, String date_from, String date_to) {
		List<Values> result = new LinkedList<>();
		if (date_from != null && date_to != null) {
			DateTime from = Values.formatter.parseDateTime(date_from);
			DateTime to = Values.formatter.parseDateTime(date_to);
			result = valuesDao.findByMeterAndDate(meterId.get(), from, to);
		} else {
			result = valuesDao.findByMeter(meterId.get());
		}
		return result;
	}

	@Path("{meterId}/valuesOBJ")
	@GET
	@UnitOfWork
	public List<Values> getMeterMetricsObjects(@PathParam("meterId") LongParam meterId,
			@QueryParam("from") String date_from, @QueryParam("to") String date_to) {
		return getValues(meterId, date_from, date_to);
	}

	/**
	 * Creates some random data for given meter
	 * 
	 * @param meterId
	 * @return
	 */
	@Path("/createDummy/{meterId}")
	@GET
	@UnitOfWork
	public Meter createValues(@PathParam("meterId") LongParam meterId) {
		Meter meter = findSafely(meterId.get());
		for (int i = 0; i < 30; i++) {
			Values value = new Values();
			value.setMeter(meter);
			value.setTemp(generateFloat(10F, 15F));
			value.setHumidity(generateFloat(40.23F, 60.5F));
			value.setPressure(generateFloat(990F, 1100F));
			value.setTimestamp(new DateTime().minusDays(generateInt(0, 30)));
			valuesDao.create(value);
		}
		return meterDao.create(meter);
	}

	/**
	 * Searches for meter, if not found , throw exception
	 * 
	 * @param personId
	 * @return
	 */
	private Meter findSafely(long personId) {
		final Optional<Meter> meter = meterDao.findById(personId);
		if (!meter.isPresent()) {
			throw new NotFoundException("Meter not found");
		}
		return meter.get();
	}

	private float generateFloat(float min, float max) {
		return min + new Random().nextFloat() * (max - min);
	}

	private int generateInt(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

}
