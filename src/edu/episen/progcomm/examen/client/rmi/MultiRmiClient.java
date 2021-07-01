package edu.episen.progcomm.examen.client.rmi;

import edu.episen.progcomm.examen.server.ex2.rmi.ConsultCompte;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class MultiRmiClient {
    private final Logger logger = Logger.getLogger(MultiRmiClient.class.getName());
    private final int PORT = 60325;
    private final String url = "rmi://localhost:" + PORT + "/rmi/compte";

    private CompletableFuture<Registry> registryFactory() {
        try {
            Registry register =  LocateRegistry.getRegistry(PORT);
            return CompletableFuture.supplyAsync(() -> register);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<CompletableFuture<Registry>> futureList() {
        List<CompletableFuture<Registry>> futures = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            futures.add(registryFactory());
        }
        return futures;
    }

    private void execute(Registry registry) {
        try {
            Random random = new Random();
            ConsultCompte service = (ConsultCompte) registry.lookup(url);
            String compte = service.getAccount(random.nextInt(3) + 1).toString();
            logger.info(compte);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void multis() {
        CompletableFuture.allOf(
                futureList().stream()
                        .map(future -> future.thenAccept(this::execute)).toArray(CompletableFuture[]::new)
        );
    }

    public static void main(String[] args) {
        new MultiRmiClient().multis();
    }
}
