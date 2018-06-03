package edu.javarx.demo.resources;

import edu.javarx.demo.model.Forecast;
import edu.javarx.demo.model.Location;
import edu.javarx.demo.model.ServiceResponse;
import edu.javarx.demo.model.Temperature;
import org.glassfish.jersey.server.Uri;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Path("reactiveForecast")
public class ForecastReactiveResource {

    @Uri("location")
    private WebTarget locationTarget;

    @Uri("temperature/{city}")
    private WebTarget temperatureTarget;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getLocationsWithTemperature(@Suspended final AsyncResponse asyncResponse){
        long startTime = System.currentTimeMillis();

        CompletionStage<List<Location>> locationsCS =
                locationTarget.request()
                    .rx()
                    .get(new GenericType<List<Location>>(){});

        final CompletionStage<List<Forecast>> forecastCS =
                locationsCS.thenCompose( locations -> {
                    List<CompletionStage<Forecast>> forecastList =
                            locations.stream().map(location -> {
                                final CompletionStage<Temperature> temperatureCompletionStage =
                                        temperatureTarget
                                        .resolveTemplate("city", location.getName())
                                        .request()
                                        .rx()
                                        .get(Temperature.class);
                                return CompletableFuture.completedFuture(
                                        new Forecast(location))
                                        .thenCombine(temperatureCompletionStage,
                                                Forecast::setTemperature);

                            }).collect(Collectors.toList());
                    return CompletableFuture.allOf(
                            forecastList.toArray(
                                    new CompletableFuture[forecastList.size()])
                            ).thenApply(v -> forecastList.stream()
                                .map(CompletionStage::toCompletableFuture)
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList())
                    );
                });
                CompletableFuture.completedFuture(
                        new ServiceResponse())
                        .thenCombine(forecastCS,
                                ServiceResponse::forecast)
                        .whenCompleteAsync((response,throwable) -> {
                            response.setProcessingTime(
                                    System.currentTimeMillis() - startTime);
                            asyncResponse.resume(response);

                        }

                );
    }
}
