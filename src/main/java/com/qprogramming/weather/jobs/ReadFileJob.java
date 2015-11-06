package com.qprogramming.weather.jobs;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.sundial.SundialJobScheduler;
import com.xeiam.sundial.annotations.SimpleTrigger;
import com.xeiam.sundial.exceptions.JobInterruptException;

@SimpleTrigger(repeatInterval = 5, timeUnit = TimeUnit.MINUTES)
public class ReadFileJob extends com.xeiam.sundial.Job {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(ReadFileJob.class);

	@Override
	public void doRun() throws JobInterruptException {
		String dataFile = (String) SundialJobScheduler.getServletContext().getAttribute("dataFile");
		LOG.info("ReadFileJob launched: Using file " + dataFile);
	}

}
