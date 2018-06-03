package edu.javarx.demo.client;

import edu.javarx.demo.model.Temperature;

import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

public class Client {

    public void callService(){
        ServiceClient serviceClient = new ServiceClient();
        CompletionStage<Response> blockingResponse = serviceClient.nonblockingCompletionResponse();
        blockingResponse.thenAccept(
                response -> {
                    Temperature t = response.readEntity(Temperature.class);
                }
        );


    }
}
