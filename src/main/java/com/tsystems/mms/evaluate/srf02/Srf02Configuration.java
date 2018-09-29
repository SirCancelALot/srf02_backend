package com.tsystems.mms.evaluate.srf02;

import gnu.io.CommPortIdentifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Enumeration;

@ConfigurationProperties(prefix = "srf02")
public class Srf02Configuration {

    private boolean simulation;

    public boolean isSimulation() {
        return this.simulation;
    }

    public void setSimulation(boolean simulation) {
        this.simulation = simulation;
    }

}
