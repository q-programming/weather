package com.qprogramming.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.qprogramming.views.IndexView;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class IndexResource {

	@GET
	public IndexView getIndex() {
		return new IndexView();
	}

}
