package com.qprogramming.weather.jobs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.sundial.SundialJobScheduler;
import com.xeiam.sundial.annotations.SimpleTrigger;
import com.xeiam.sundial.exceptions.JobInterruptException;

import au.com.bytecode.opencsv.CSVReader;

@SimpleTrigger(repeatInterval = 5, timeUnit = TimeUnit.MINUTES)
public class ReadFileJob extends com.xeiam.sundial.Job {

	private static final char SEPARATOR = ';';
	private static final Logger LOG = LoggerFactory.getLogger(ReadFileJob.class);

	@Override
	public void doRun() throws JobInterruptException {
		String dataFile = (String) SundialJobScheduler.getServletContext().getAttribute("dataFile");
		try {
			CSVReader cvsReader = new CSVReader(new FileReader(dataFile),SEPARATOR);
			String[] nextLine;
			while ((nextLine = cvsReader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				System.out.println("row" + nextLine[0] + nextLine[1] + "etc...");
			}
			cvsReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LOG.info("ReadFileJob launched: Using file " + dataFile);
	}

}
