package com.qprogramming.weather.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Entity
@Table(name = "metrics")
@NamedQueries({
		@NamedQuery(name = "com.qprogramming.weather.core.Values.findByMeter", query = "FROM Values v WHERE v.meter LIKE :meter ORDER BY v.timestamp ASC"),
		@NamedQuery(name = "com.qprogramming.weather.core.Values.findByMeterAndDate", query = "FROM Values v WHERE v.meter LIKE :meter AND v.timestamp >= :date_from AND v.timestamp <= :date_to ORDER BY v.timestamp ASC") })
public class Values {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "timestamp", nullable = false)
	private DateTime timestamp;
	@Column(name = "temp", nullable = false)
	private Float temp;
	@Column(name = "humidity", nullable = false)
	private Float humidity;
	@Column(name = "pressure", nullable = false)
	private Float pressure;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "meter")
	private Meter meter;

	public static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public String getDate() {
		return formatter.print(timestamp);
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	public Float getHumidity() {
		return humidity;
	}

	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}

	public Float getPressure() {
		return pressure;
	}

	public void setPressure(Float pressure) {
		this.pressure = pressure;
	}

	public Meter getMeter() {
		return meter;
	}

	public void setMeter(Meter meter) {
		this.meter = meter;
	}

}
