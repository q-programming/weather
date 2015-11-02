package com.qprogramming.weather.views;

import io.dropwizard.views.View;

public class IndexView extends View {

	private String view = "Meteo station index";

	public IndexView() {
		super("index.ftl");
	}

	public String getView() {
		return view;
	}

}
