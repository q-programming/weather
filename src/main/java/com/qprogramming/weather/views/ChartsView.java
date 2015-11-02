package com.qprogramming.weather.views;

import io.dropwizard.views.View;

public class ChartsView extends View {

	private String view = "Meteo station charts";
	private String meter;

	public ChartsView(String meter) {
		super("charts.ftl");
		this.meter = meter;
	}

	public String getMeter() {
		return meter;
	}

	public void setMeter(String meter) {
		this.meter = meter;
	}

	public String getView() {
		return view;
	}

}
