package com.qprogramming.weather;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.xeiam.dropwizard.sundial.SundialConfiguration;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class WeatherConfiguration extends Configuration {
	@NotEmpty
	private String template;

	@NotEmpty
	private String defaultName = "Stranger";

	@NotEmpty
	private Map<String, Map<String, String>> viewRendererConfiguration = Collections.emptyMap();

	@Valid
	@NotNull
	private DataSourceFactory database = new DataSourceFactory();

	@Valid
	@NotNull
	public SundialConfiguration sundialConfiguration = new SundialConfiguration();
	
	@JsonProperty("dataFile")
	private String dataFile;

	@JsonProperty("sundial")
	public SundialConfiguration getSundialConfiguration() {
		return sundialConfiguration;
	}

	@JsonProperty("database")
	public DataSourceFactory getDataSourceFactory() {
		return database;
	}

	@JsonProperty("database")
	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.database = dataSourceFactory;
	}

	@JsonProperty
	public String getTemplate() {
		return template;
	}

	@JsonProperty
	public void setTemplate(String template) {
		this.template = template;
	}

	@JsonProperty
	public String getDefaultName() {
		return defaultName;
	}

	@JsonProperty
	public void setDefaultName(String name) {
		this.defaultName = name;
	}

	@JsonProperty("viewRendererConfiguration")
	public Map<String, Map<String, String>> getViewRendererConfiguration() {
		return viewRendererConfiguration;
	}

	@JsonProperty("viewRendererConfiguration")
	public void setViewRendererConfiguration(Map<String, Map<String, String>> viewRendererConfiguration) {
		ImmutableMap.Builder<String, Map<String, String>> builder = ImmutableMap.builder();
		for (Map.Entry<String, Map<String, String>> entry : viewRendererConfiguration.entrySet()) {
			builder.put(entry.getKey(), ImmutableMap.copyOf(entry.getValue()));
		}
		this.viewRendererConfiguration = builder.build();
	}

	public String getDataFile() {
		return dataFile;
	}

	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}
}
