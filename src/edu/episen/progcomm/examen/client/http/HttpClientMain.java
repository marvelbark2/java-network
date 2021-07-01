package edu.episen.progcomm.examen.client.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class HttpClientMain {
    public static void main(String[] args) throws Exception {
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

        CompletableFuture<Void> client = HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);
        client.join();
    }
}
