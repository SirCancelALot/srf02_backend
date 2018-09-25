package com.tsystems.mms.evaluate.srf02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gnu.io.*;

@EnableConfigurationProperties({Srf02Configuration.class})
@Configuration
public class DistanceMeasurementConfiguration {
	
	private final Srf02Configuration srf02Configuration;
	
	public DistanceMeasurementConfiguration(@Autowired Srf02Configuration srf02UsbConfiguration) {
		this.srf02Configuration = srf02UsbConfiguration;
	}
	
	@Bean 
	public DistanceMeasurementProvider getDistanceMeasurementProvider() throws Exception {
		boolean isSimulated = true;
		//String commPort = findPort();
		if(isSimulated) {
			System.out.println("Starting in SIMULATION mode");
			return new SimulationDistanceProvider();
		} else {
			System.out.println("Starting in SENSOR mode");

			return new Srf02Connector("COM7");
		}
	}

	/**private String findPort() throws Exception {
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();

		while ( portEnum.hasMoreElements() )
		{
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			if(portIdentifier.isCurrentlyOwned()){
				continue;
			}
			System.out.println(portIdentifier.getName());
			return portIdentifier.getName();
		}
		return null;
	}**/
}
