package edu.episen.progcomm.examen.client.tcp;

import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Logger;

public class TcpClient {
    final static Logger logger = Logger.getLogger(TcpClient.class.getName());

    public static void main(String[] args) throws Exception {
        logger.info("TcpClient started");

        final Options options = new Options();
        final CommandLineParser commandParser = new DefaultParser();


        final Option fromOpt = Option.builder().longOpt("from-account").hasArg().build();
        options.addOption(fromOpt);

        final Option toOpt = Option.builder().longOpt("to-account").hasArg().build();
        options.addOption(toOpt);
        final Option amountOpt = Option.builder().longOpt("amount").hasArg().build();
        options.addOption(amountOpt);

        CommandLine cli = commandParser.parse(options, args);

        int from = 1;
        if(cli.hasOption(fromOpt.getLongOpt()))
            from = Integer.parseInt(cli.getOptionValue(fromOpt.getLongOpt()));

        int to = 2;
        if(cli.hasOption(toOpt.getLongOpt()))
            to = Integer.parseInt(cli.getOptionValue(toOpt.getLongOpt()));

        int amount = 20;
        if(cli.hasOption(amountOpt.getLongOpt()))
            amount = Integer.parseInt(cli.getOptionValue(amountOpt.getLongOpt()));

        StringBuilder builder = new StringBuilder();
        builder.append(from);
        builder.append("-");
        builder.append(to);
        builder.append("-");
        builder.append(amount);

        String request = builder.toString();

        logger.info("Request to be sent: " + request);

        final int PORT = 63204;
        final String hostName = "localhost";
        final InetAddress HOST = InetAddress.getByName(hostName);
        Socket socket = new Socket(HOST,PORT);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        writer.println(request);

        String response = reader.readLine();
        logger.info(response);


    }
}
