package com.qprogramming.weather.api;

public class MeterData {
	private String meter;
	private Object[][] temperature;
	private Object[][] humidity;
	private Object[][] pressure;

	public MeterData(int size) {
		temperature = new Object[size][2];
		humidity = new Object[size][2];
		pressure = new Object[size][2];
	}

	public String getMeter() {
		return meter;
	}

	public void setMeter(String meter) {
		this.meter = meter;
	}

	public Object[][] getTemperature() {
		return temperature;
	}

	public void setTemperature(int pos, Object timestamp, Object temperature) {
		this.temperature[pos][0] = timestamp;
		this.temperature[pos][1] = temperature;
	}

	public Object[][] getHumidity() {
		return humidity;
	}

	public void setHumidity(int pos, Object timestamp, Object humidity) {
		this.humidity[pos][0] = timestamp;
		this.humidity[pos][1] = humidity;
	}

	public Object[][] getPressure() {
		return pressure;
	}

	public void setPressure(int pos, Object timestamp, Object pressure) {
		this.pressure[pos][0] = timestamp;
		this.pressure[pos][1] = pressure;
	}

}
