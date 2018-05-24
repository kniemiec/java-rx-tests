package edu.javarx.demo.client;


import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

public class ServiceClient {

    public Response blockingResponseFromService(){
        Response response =  ClientBuilder.newClient()
                    .target("http://localhost:8080/service-rul")
                    .request()
                    .get();
        return response;
    }

    public Future<Response> nonblockingResponse(){
        Future<Response> response =  ClientBuilder.newClient()
                .target("http://localhost:8080/service-rul")
                .request()
                .async()
                .get();

        return response;

    }


    public CompletionStage<Response> nonblockingCompletionResponse(){
        CompletionStage<Response> response =  ClientBuilder.newClient()
                .target("http://localhost:8080/service-rul")
                .request()
                .rx()
                .get();

        return response;

    }

}
