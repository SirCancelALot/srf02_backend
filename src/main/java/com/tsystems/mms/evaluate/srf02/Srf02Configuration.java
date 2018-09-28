package com.tsystems.mms.evaluate.srf02;

import gnu.io.CommPortIdentifier;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Enumeration;

@ConfigurationProperties(prefix = "srf02")
public class Srf02Configuration {
    private Srf02Connector connector;
    private ArrayList<String> ports;

    private Srf02Configuration() throws Exception {
        ports = findPorts();
        connector = choosePort();
        connector.findAndSetAddress();
        /**System.out.println(connector.testCommunication());
        System.out.println("tada");**/
    }

    private ArrayList<String> findPorts(){
        ArrayList<String> ports = new ArrayList<>();
        Enumeration<CommPortIdentifier> commPortIdentifierEnumeration = CommPortIdentifier.getPortIdentifiers();
        while (commPortIdentifierEnumeration.hasMoreElements()){
            CommPortIdentifier commPortIdentifier = commPortIdentifierEnumeration.nextElement();
            System.out.println(commPortIdentifier.getName());
            ports.add(commPortIdentifier.getName());
        }
        return ports;
    }

    private Srf02Connector choosePort() throws Exception{
        Srf02Connector connectortmp = null;
        for (int i = 0; i<ports.size(); i++){
            try {
                connectortmp = new Srf02Connector(ports.get(i));
                if(connectortmp.testUSBVersion()!=-1){
                    break;
                }
                connectortmp.close();
            }catch (Exception e){
                throw e;
            }

        }
        return connectortmp;

    }

    public Srf02Connector getConnector() {
        return connector;
    }
}
