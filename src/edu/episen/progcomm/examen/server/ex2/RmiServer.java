package edu.episen.progcomm.examen.server.ex2;

import edu.episen.progcomm.examen.server.ex2.rmi.ConsultCompte;
import edu.episen.progcomm.examen.server.ex2.rmi.ConsultCompteImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class RmiServer {
    final static Logger logger = Logger.getLogger(RmiServer.class.getName());
    public static void main(String[] args) throws RemoteException {
        logger.info("RMI server started");
        final int PORT = 60325;
        final String url = "rmi://localhost:" + PORT + "/rmi/compte";

        Registry registry = LocateRegistry.createRegistry(PORT);

        ConsultCompte od = new ConsultCompteImpl();

        registry.rebind(url, od);

        logger.info("Rmi Server is able to receive clients");
    }
}
