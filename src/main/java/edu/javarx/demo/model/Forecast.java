package edu.javarx.demo.model;

public class Forecast {

    private Location location;
    private Temperature temperature;

    public Forecast(Location location){
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }
}
