package com.tsystems.mms.evaluate.srf02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class DistanceController {

    @Autowired
    private DistanceService distanceService;

    @RequestMapping("/distance")
    public Distance distance() throws Exception{
        return distanceService.measure();
    }
}
