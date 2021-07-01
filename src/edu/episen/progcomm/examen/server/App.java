package edu.episen.progcomm.examen.server;

import edu.episen.progcomm.examen.server.ex1.TCPServer;
import edu.episen.progcomm.examen.server.ex2.RmiServer;
import edu.episen.progcomm.examen.server.ex3.UDPServer;
import edu.episen.progcomm.examen.server.ex4.HttpServerMain;

public class App {
    public static void main(String[] args) throws Exception {
        RmiServer.main(args);
        UDPServer.main(args);
        HttpServerMain.main(args);
        TCPServer.main(args);

    }
}
