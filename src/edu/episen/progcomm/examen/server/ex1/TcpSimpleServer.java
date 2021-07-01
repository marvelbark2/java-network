package edu.episen.progcomm.examen.server.ex1;

import edu.episen.progcomm.examen.server.shared.services.CompteService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TcpSimpleServer {
    final static Logger logger = Logger.getLogger(TcpSimpleServer.class.getName());
    public static void main(String[] args) throws Exception {
        final int PORT = 63204;

        logger.info("Server App started");

        ServerSocket server = new ServerSocket(PORT);

        logger.info("Server TCP started on " + PORT);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Socket socket = server.accept();
                        InputStream inputStream = socket.getInputStream();
                        OutputStream outputStream = socket.getOutputStream();
                        while (true) {
                            while (inputStream.available() == 0)
                                Thread.sleep(500);
                            byte[] bytes = new byte[inputStream.available()];
                            inputStream.read(bytes);
                            String msg = new String(bytes);
                            logger.info("received: " + msg);

                            String response = "You sent " + msg;
                            byte[] res = response.getBytes(StandardCharsets.UTF_8);
                            outputStream.write(res);
                            outputStream.flush();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        thread.join();
    }
}
