package com.tsystems.mms.evaluate.srf02;

import java.io.IOException;
import java.util.Random;

public class SimulationDistanceProvider implements DistanceMeasurementProvider
{
	private static final int DISTANCE_MAX = 300;
	private static final int DISTANCE_MIN = 0;
	private int distanceCentimeters = 50;		// initial
	private Random random;
	
	public SimulationDistanceProvider() {
		random = new Random();
	}
	
	@Override
	public double getDistance() throws IOException, InterruptedException {
		int delta = random.nextInt(20);
		if (random.nextBoolean()) {
			// UP
			distanceCentimeters += delta;
		} else {
			// DOWN
			distanceCentimeters -= delta;
		}
		
		if(distanceCentimeters > DISTANCE_MAX) {
			distanceCentimeters = DISTANCE_MAX;
		}
		if(distanceCentimeters < DISTANCE_MIN) {
			distanceCentimeters = DISTANCE_MIN;
		}
		
		return (double) distanceCentimeters;
	}
	
	
}
