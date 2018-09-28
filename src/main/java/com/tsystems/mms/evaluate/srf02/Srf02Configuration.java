package com.tsystems.mms.evaluate.srf02;

import gnu.io.CommPortIdentifier;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Enumeration;

@ConfigurationProperties(prefix = "srf02")
public class Srf02Configuration {
    private Srf02Connector connector;
    private ArrayList<String> ports;
    private boolean deviceAvailable = false;

    private Srf02Configuration() throws Exception {
        Srf02Application.LOGGER.fine("Start looking for and Configuring Device");
        ports = findPorts();
        Srf02Application.LOGGER.fine("Availabale Ports are:" + ports.toString());
        if(ports.isEmpty()){
            Srf02Application.LOGGER.warning("No ports available!!! starting in Simulationmode.");
            deviceAvailable = false;
            return;
        }
        connector = choosePort();
        if(connector == null){
            Srf02Application.LOGGER.warning("Didn't find any Device in one of the available Ports!!! starting in Simulationmode.");
            deviceAvailable = false;
            return;
        }
        connector.findAndSetAddress();
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

    private Srf02Connector choosePort() throws Exception{
        Srf02Connector connectortmp = null;
        try {
            for (int i = 0; i<ports.size(); i++){

                connectortmp = new Srf02Connector(ports.get(i));
                if(connectortmp.testUSBVersion()!=-1){
                    deviceAvailable = true;
                    break;
                }
                connectortmp.close();
            }
        }catch (Exception e){
            throw e;
        }
        return connectortmp;
    }

    public Srf02Connector getConnector() {
        return connector;
    }

    public boolean DeviceAvailable(){return deviceAvailable;}
}
