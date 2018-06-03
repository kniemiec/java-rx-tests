package edu.javarx.demo.model;

import java.util.ArrayList;
import java.util.List;

public class ServiceResponse {
    public long getProcessingTime() {
        return processingTime;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    private long processingTime;

    private List<Forecast> forecasts = new ArrayList<>();

    public void setProcessingTime(long processingTime){
        this.processingTime = processingTime;
    }

    public ServiceResponse forecast(List<Forecast> forecasts){
        this.forecasts = forecasts;
        return this;
    }



}
