package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.ClearRequest;
import responses.ClearResponse;
import services.ClearService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClearHandler implements HttpHandler {

    private static Logger logger = Logger.getLogger("ClearHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            //  Expect a POST requests
            if(exchange.getRequestMethod().toUpperCase().equals("POST")) {

                //  Parse request body into RegisterRequest object
                ClearRequest req =
                        JSONHandler.createRequest(exchange.getRequestBody(),
                                ClearRequest.class);

                //  Pass the request to the RegisterService to get the response data
                ClearResponse res = new ClearService().handleRequest(req);

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
