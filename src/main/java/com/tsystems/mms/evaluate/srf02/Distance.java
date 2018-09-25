package com.tsystems.mms.evaluate.srf02;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Distance {

	public static final String UNIT_MM = "mm";
	public static final String UNIT_CM = "cm";
	public static final String UNIT_M =  "m";
	public static final String UNIT_KM = "km";
	
	private final double distance;
	
	private final String unit;
	
	public Distance(double distance, String unit) {
		this.distance = distance;
		this.unit = unit;
	}

	public double getDistance() {
		return distance;
	}

	public String getUnit() {
		return unit;
	}
	
}
