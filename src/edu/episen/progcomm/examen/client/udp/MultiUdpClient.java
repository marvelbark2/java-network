package edu.episen.progcomm.examen.client.udp;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class MultiUdpClient {
    private final Logger logger = Logger.getLogger(MultiUdpClient.class.getName());

    private CompletableFuture<DatagramSocket> datagramSocketFactory() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
                return null;
            }
        });
    }
    private List<CompletableFuture<DatagramSocket>> futureList() {
        List<CompletableFuture<DatagramSocket>> futures = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            futures.add(datagramSocketFactory());
        }
        return futures;
    }

    private String randomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        final Random RANDOM = new SecureRandom();
        final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    private void sendRequest(DatagramSocket socket) {
        logger.info(socket.toString());
        try {
            final int PORT = 63207;
            final InetAddress HOST = InetAddress.getLocalHost();

            int id = 1;
            String rib = randomString(10);

            String request = id + "-"+rib;

            byte[] buf = request.getBytes(StandardCharsets.UTF_8);

            DatagramPacket dp = new DatagramPacket(buf, buf.length, HOST, PORT);

            socket.send(dp);

            logger.info("UDP client sent : " + request);

            byte[] response = new byte[254];

            DatagramPacket dbr = new DatagramPacket(response, response.length, dp.getAddress(), dp.getPort());
            socket.receive(dbr);
            String res = new String(dbr.getData(), dbr.getOffset(), dbr.getLength());

            logger.info("UDP client received " + res);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getLocalizedMessage());
        }
    }

    public void multiS() {
        CompletableFuture.allOf(
                futureList().stream()
                        .map(future -> future.thenAccept(this::sendRequest)).toArray(CompletableFuture[]::new)
        );
    }

    public static void main(String[] args) {
        new MultiUdpClient().multiS();
    }

}
