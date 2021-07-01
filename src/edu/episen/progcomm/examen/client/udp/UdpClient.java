package edu.episen.progcomm.examen.client.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class UdpClient {
    final static Logger logger = Logger.getLogger(UdpClient.class.getName());
    public static void main(String[] args) throws Exception {

        logger.info("UDP client started");

        final int PORT = 63207;
        final InetAddress HOST = InetAddress.getLocalHost();
        DatagramSocket socket = new DatagramSocket();

        int id = 1;
        String rib = "FR5024522445";

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
    }
}
