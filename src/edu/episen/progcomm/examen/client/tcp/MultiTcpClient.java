package edu.episen.progcomm.examen.client.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class MultiTcpClient {
    private final Logger logger = Logger.getLogger(MultiTcpClient.class.getName());
    private CompletableFuture<Socket> socketFactory() {
        Socket socket = null;
        try {
            final int PORT = 63205;
            final String hostName = "localhost";
            final InetAddress HOST = InetAddress.getByName(hostName);
            socket = new Socket(HOST, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket finalSocket = socket;
        return CompletableFuture.supplyAsync(() -> finalSocket);
    }

    private List<CompletableFuture<Socket>> futureList() {
        List<CompletableFuture<Socket>> futures = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            futures.add(socketFactory());
        }
        return futures;
    }

    private void sendSocket(Socket socket) {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder builder = new StringBuilder();
            int from = 1;
            int to = 2;
            int amount = 20;
            builder.append(from);
            builder.append("-");
            builder.append(to);
            builder.append("-");
            builder.append(amount);

            String request = builder.toString();
            writer.println(request);

            String response = reader.readLine();
            logger.info(response);
        } catch (Exception e) {
            logger.severe(e.getLocalizedMessage());
        }
    }

    public void multis() {
        CompletableFuture.allOf(
                futureList().stream()
                        .map(future -> future.thenAccept(this::sendSocket)).toArray(CompletableFuture[]::new)
        );
    }

    public static void main(String[] args) {
        MultiTcpClient client = new MultiTcpClient();
        client.multis();
    }

}
