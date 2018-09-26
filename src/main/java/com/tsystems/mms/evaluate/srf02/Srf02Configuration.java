package com.tsystems.mms.evaluate.srf02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "srf02")
public class Srf02Configuration {
    private Srf02Connector connector;

    private Srf02Configuration(@Autowired Srf02Connector connector){
        this.connector = connector;
    }

    private findPort

    public Srf02Connector getConnector() {
        return connector;
    }
}
