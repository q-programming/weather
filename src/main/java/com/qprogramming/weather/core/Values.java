package com.qprogramming.weather.core;

import java.util.Date;

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

@Entity
@Table(name = "metrics")
@NamedQueries({
    @NamedQuery(
            name = "com.qprogramming.weather.core.Values.findByMeter",
            query = "FROM Values v WHERE v.meter LIKE :meter"
    )
})
public class Values {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "timestamp", nullable = false)
	private Date timestamp;
	@Column(name = "temp", nullable = false)
	private Float temp;
	@Column(name = "humidity", nullable = false)
	private Float humidity;
	@Column(name = "pressure", nullable = false)
	private Float pressure;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "meter")
	private Meter meter;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
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
