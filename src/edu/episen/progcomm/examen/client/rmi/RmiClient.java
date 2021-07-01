package edu.episen.progcomm.examen.client.rmi;

import edu.episen.progcomm.examen.server.ex2.rmi.ConsultCompte;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class RmiClient {
    final static Logger logger = Logger.getLogger(RmiClient.class.getName());
    public static void main(String[] args) throws Exception {
        logger.info("RMI client started");
        final int PORT = 60325;
        final String url = "rmi://localhost:" + PORT + "/rmi/compte";


        Registry register =  LocateRegistry.getRegistry(PORT);

        ConsultCompte service = (ConsultCompte) register.lookup(url);

        String compte = service.getAccount(2).toString();

        logger.info(compte);
    }
}
