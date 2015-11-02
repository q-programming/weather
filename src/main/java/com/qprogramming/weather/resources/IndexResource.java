package com.qprogramming.weather.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.qprogramming.weather.views.ChartsView;
import com.qprogramming.weather.views.IndexView;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class IndexResource {

	@GET
	public IndexView getIndex() {
		return new IndexView();
	}

	@GET
	@Path("charts/{meterId}")
	public ChartsView getCharts(@PathParam("meterId") String meterID) {
		return new ChartsView(meterID);
	}

}
