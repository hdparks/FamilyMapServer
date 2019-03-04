package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.PersonRequest;
import responses.PersonResponse;
import services.PersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonHandler implements HttpHandler {

    private static Logger logger = Logger.getLogger("PersonHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {

            //  Expect a POST requests
            if(exchange.getRequestMethod().toUpperCase().equals("POST")) {

                //  Parse request body into RegisterRequest object
                PersonRequest req =
                        JSONHandler.createRequest(exchange.getRequestBody(),
                                PersonRequest.class);

                //  Pass the request to the RegisterService to get the response data
                PersonResponse res = new PersonService().handleRequest(req);

                //  Convert response content to JSON
                String json = JSONHandler.generateResponseJSON(res);

                //  Write to the response object
                OutputStream responseBody = exchange.getResponseBody();
                JSONHandler.writeString(json, responseBody);

            } else {
                //  We expected a POST requests to this endpoint.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
            }

        } catch (IOException ex){
            //  Something went wrong on the server side, so we return
            //  "Internal Service Error"
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            logger.log(Level.SEVERE,ex.getMessage());
        }
        exchange.close();
    }
}
