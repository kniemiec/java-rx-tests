package edu.javarx.demo;


import javax.ws.rs.core.Application;
import java.util.Set;

/**
 *
 * @author Juneau
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(edu.javarx.demo.resources.ForecastResource.class);
        resources.add(edu.javarx.demo.resources.LocationResource.class);
        resources.add(edu.javarx.demo.resources.TemperatureResource.class);
        resources.add(edu.javarx.demo.resources.ForecastReactiveResource.class);
        return resources;
    }
}