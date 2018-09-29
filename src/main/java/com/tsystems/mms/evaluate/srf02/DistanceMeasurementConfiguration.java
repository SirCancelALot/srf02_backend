package com.tsystems.mms.evaluate.srf02;

import gnu.io.CommPortIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Enumeration;


@EnableConfigurationProperties({Srf02Configuration.class})
@Configuration
public class DistanceMeasurementConfiguration {
	private final Srf02Configuration srf02Configuration;

	public DistanceMeasurementConfiguration(@Autowired Srf02Configuration srf02UsbConfiguration) {
		this.srf02Configuration = srf02UsbConfiguration;
	}
	
	@Bean 
	public DistanceMeasurementProvider getDistanceMeasurementProvider() throws Exception {
		Srf02Connector connector = null;
		boolean isSimulation = srf02Configuration.isSimulation();
		if (!isSimulation){
			connector = getConnector();
			if (connector == null){
				isSimulation = true;
			}
		}

		if(isSimulation) {
			System.out.println("Starting in SIMULATION mode");
			return new SimulationDistanceProvider();
		} else {
			System.out.println("Starting in SENSOR mode");
			return connector;
		}
	}
	private Srf02Connector getConnector() throws Exception {
		Srf02Connector tmpConnector;
		ArrayList<String> ports;
		Srf02Application.LOGGER.fine("Start looking for and Configuring Device");
		ports = findPorts();
		Srf02Application.LOGGER.fine("Availabale Ports are: " + ports.toString());
		if(ports.isEmpty()){
			Srf02Application.LOGGER.warning("No ports available!!! Starting in SIMULATION mode.");
			return null;
		}
		tmpConnector = choosePort(ports);
		if(tmpConnector == null){
			Srf02Application.LOGGER.warning("Didn't find any Device in one of the available Ports!!! Starting in SIMULATION mode.");
			return null;
		}
		tmpConnector.findAndSetAddress();
		return tmpConnector;
	}

	private ArrayList<String> findPorts(){
		ArrayList<String> ports = new ArrayList<>();
		Enumeration<CommPortIdentifier> commPortIdentifierEnumeration = CommPortIdentifier.getPortIdentifiers();
		while (commPortIdentifierEnumeration.hasMoreElements()){
			CommPortIdentifier commPortIdentifier = commPortIdentifierEnumeration.nextElement();
			ports.add(commPortIdentifier.getName());
		}
		return ports;
	}

	private Srf02Connector choosePort(ArrayList<String> ports) throws Exception{
		Srf02Connector connectortmp = null;
		try {
			for (int i = 0; i<ports.size(); i++){
				String port = ports.get(i);

				connectortmp = new Srf02Connector(port);
				if(connectortmp.testUSBVersion()!=-1){
					Srf02Application.LOGGER.fine("Connected to Port: " + port);
					break;
				}
				connectortmp.close();
				connectortmp = null;
			}
		}catch (Exception e){
			throw e;
		}
		return connectortmp;
	}

}
