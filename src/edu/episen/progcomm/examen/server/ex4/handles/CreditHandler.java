package edu.episen.progcomm.examen.server.ex4.handles;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.episen.progcomm.examen.server.shared.models.Compte;
import edu.episen.progcomm.examen.server.shared.models.Credit;
import edu.episen.progcomm.examen.server.shared.services.CompteService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class CreditHandler implements HttpHandler {
    private final Logger logger = Logger.getLogger(CreditHandler.class.getName());
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpMethod = exchange.getRequestMethod();
        String route = exchange.getRequestURI().getPath();

        logger.info("Receiving a http request !");

        String appRoute = "/compte/credit";
        String msg;
        if(route.equals(appRoute) && httpMethod.equals("POST")) {
            CompteService service = CompteService.Instance;
            byte[] bodyBytes = new byte[256];
            exchange.getRequestBody().read(bodyBytes);
            JsonNode body = mapper.readTree(bodyBytes);

            try {
                int id = body.get("id").asInt();
                Double amount = body.get("amount").asDouble();
                String motif = body.get("purpose").asText();
                Compte compte = service.getAccoutById(id);
                Credit credit = new Credit(compte, amount, motif);
                logger.info(credit.getCompte().toString());
                msg = "La demande de credit est bien faite";
                exchange.sendResponseHeaders(200, msg.length());
            } catch (Exception e) {
                logger.info("Serialization error");
                msg = "Error! Service cannot recognize request";
                exchange.sendResponseHeaders(200, msg.length());
            }
        } else {
            logger.info("404 error");
            msg = "Error Not Found";
            exchange.sendResponseHeaders(404, msg.length());
        }

        OutputStream os = exchange.getResponseBody();
        PrintWriter writer = new PrintWriter(os);
        writer.print(msg);
        writer.close();
    }
}
