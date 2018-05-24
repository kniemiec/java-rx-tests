package edu.javarx.demo.resources;

import edu.javarx.demo.model.Location;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/location")
public class LocationResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocations(){
        List<Location> locations = new ArrayList<Location>();
        locations.add(new Location("London"));

        return Response.ok(
                new GenericEntity<List<Location>>(locations){})
                .build();
    }
}
