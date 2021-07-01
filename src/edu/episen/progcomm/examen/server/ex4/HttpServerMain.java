package edu.episen.progcomm.examen.server.ex4;

import com.sun.net.httpserver.HttpServer;
import edu.episen.progcomm.examen.server.ex4.handles.CreditHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class HttpServerMain {
    final static Logger logger = Logger.getLogger(HttpServerMain.class.getName());
    public static void main(String[] args) throws Exception {
        logger.info("Http app is running");

        final int PORT = 63207;

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", new CreditHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();

        logger.info("Http SERVER is running on " + PORT);
    }
}
