package com.tsystems.mms.evaluate.srf02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.*;

@Service
public class DistanceService {

    @Autowired
    private DistanceMeasurementProvider distanceMeasurementProvider;

    public DistanceService(){

    }

    public Distance measure() throws Exception{
        double distance = distanceMeasurementProvider.getDistance();
        Srf02Application.LOGGER.fine("Mesurement Result: " + distance + " " + Distance.UNIT_CM);
        if (distance>300 || distance<20){
            Srf02Application.LOGGER.warning("Value out of measuring range! Try again");
            distance = -1;
        }
        return new Distance(distance, Distance.UNIT_CM);
    }
}
