package com.tsystems.mms.evaluate.srf02;

import java.io.IOException;

public interface DistanceMeasurementProvider {
	
	/**
	 * Return a measured distance in centimeters.
	 * @return
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public double getDistance() throws IOException, InterruptedException;

}
