package com.tsystems.mms.evaluate.srf02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.*;

@Service
public class DistanceService {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private DistanceMeasurementProvider distanceMeasurementProvider;

    public DistanceService(){

    }

    public Distance measure() throws Exception{
        return new Distance(distanceMeasurementProvider.getDistance(), Distance.UNIT_CM);
    }
}
