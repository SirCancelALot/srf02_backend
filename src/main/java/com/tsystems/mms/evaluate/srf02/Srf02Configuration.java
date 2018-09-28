package com.tsystems.mms.evaluate.srf02;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "srf02")
public class Srf02Configuration {
    private Srf02Connector connector;

    private Srf02Configuration() throws Exception {
        connector = new Srf02Connector("COM7");
        System.out.println(connector.testCommunication());
        System.out.println("tada");
    }

    private void findPort(){

    }

    public Srf02Connector getConnector() {
        return connector;
    }
}
