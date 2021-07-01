package edu.episen.progcomm.examen.client.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MultiHttpClient {

    private CompletableFuture<HttpClient> httpClientFactory() {
        HttpClient client = HttpClient.newHttpClient();
        return CompletableFuture.supplyAsync(() -> client);
    }

    private List<CompletableFuture<HttpClient>> futureList() {
        List<CompletableFuture<HttpClient>> futures = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            futures.add(httpClientFactory());
        }
        return futures;
    }

    private void postRequest(HttpClient client) {
       try {
           final String uri = "http://localhost:63207/compte/credit";
           ObjectMapper objectMapper = new ObjectMapper();
           Map body = Map.of(
                   "id", 1,
                   "amount", 1500,
                   "purpose", "Conso"
           );
           String requestBody = objectMapper.writeValueAsString(body);
           System.out.println(requestBody);
           HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
                   .header("Content-Type", "application/json")
                   .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                   .build();
           var send = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                   .thenApply(HttpResponse::body)
                   .thenAccept(System.out::println);
           send.join();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    public void multis() {
        CompletableFuture.allOf(
                futureList().stream()
                        .map(future -> future.thenAccept(this::postRequest)).toArray(CompletableFuture[]::new)
        );
    }

    public static void main(String[] args) {
        new MultiHttpClient().multis();
    }
}
