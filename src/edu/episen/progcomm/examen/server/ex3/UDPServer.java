package edu.episen.progcomm.examen.server.ex3;

import edu.episen.progcomm.examen.server.shared.models.Compte;
import edu.episen.progcomm.examen.server.shared.services.CompteService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class UDPServer {
    final static Logger logger = Logger.getLogger(UDPServer.class.getName());
    public static void main(String[] args) throws Exception{
        final int PORT = 63207;

        logger.info("UDPServer App started");

        DatagramSocket socket = new DatagramSocket(PORT);
        byte[] buf = new byte[256];

        logger.info("Server UDP started on " + PORT);

        ExecutorService executor = Executors.newCachedThreadPool();

        CompteService service = CompteService.Instance;

        logger.info("Waiting for client");

        executor.execute(() -> {
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);

                    String received = new String(packet.getData(), 0, packet.getLength());

                    byte[] responseByte = null;

                    try {
                        String[] data = received.split("-");

                        int id = Integer.parseInt(data[0]);
                        String rib = data[1];

                        Compte compte = service.changeRibAccount(id, rib);

                        responseByte = compte.toString().getBytes(StandardCharsets.UTF_8);


                    } catch (Exception e) {
                        responseByte = "Erreur! Impossible pour etablir vos commandes".getBytes(StandardCharsets.UTF_8);
                    }
                    DatagramPacket response = new DatagramPacket(responseByte, responseByte.length, packet.getAddress(), packet.getPort());
                    socket.send(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
