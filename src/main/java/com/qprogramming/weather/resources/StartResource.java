package com.qprogramming.weather.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

@Path("/start")
public class StartResource {
	
	public static final String GREATING = "Hello , welcome to Weather app";

	@GET
	@Timed
	@Produces(MediaType.TEXT_PLAIN)
	public String getGreating(){
		return GREATING;
	}

}
