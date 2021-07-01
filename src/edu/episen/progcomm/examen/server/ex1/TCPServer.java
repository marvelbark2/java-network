package edu.episen.progcomm.examen.server.ex1;

import edu.episen.progcomm.examen.server.shared.models.Compte;
import edu.episen.progcomm.examen.server.shared.services.CompteService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TCPServer {
    final static Logger logger = Logger.getLogger(TCPServer.class.getName());
    public static void main(String[] args) throws Exception {
        final int PORT = 63204;

        logger.info("Server App started");

        ServerSocket server = new ServerSocket(PORT);
        logger.info("Server TCP started on " + PORT);

        ExecutorService executor = Executors.newCachedThreadPool();

        CompteService service = CompteService.Instance;
        while (true) {
            logger.info("Waiting for client");
            Socket socket = server.accept();
            executor.execute(() -> {
                try {
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    logger.info("User connected ");
                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        try {
                            logger.info(" Receiving from user: " + msg);
                            String[] data = msg.split("-");
                            int fromId = Integer.parseInt(data[0]);
                            Compte from = service.getAccoutById(fromId);
                            int toId = Integer.parseInt(data[1]);
                            Compte to = service.getAccoutById(toId);
                            double amount = Double.parseDouble(data[2]);

                            if(from.getSolde() >= amount) {
                                from.setSolde(from.getSolde() - amount);
                                to.setSolde(to.getSolde() + amount);
                                writer.println("virement est bien fait");
                                logger.info("" + to + " -> " + from);
                            } else {
                                writer.println("Erreur! Solde insuffisant");
                            }
                        } catch (Exception e) {
                            writer.println("Erreur, il n'existe pas ce que vous demandez");
                        }
                    }
                } catch (IOException e) {
                    try {
                        socket.close();
                        executor.shutdown();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            });
        }
    }
}
