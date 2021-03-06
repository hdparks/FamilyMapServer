package handlers;

import com.sun.net.httpserver.HttpExchange;
import responses.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.util.logging.Logger;

public class ExchangeUtilities {

    private static Logger logger = Logger.getLogger("ExchangeUtilities");

    public static <T> void writeResponseToHttpExchange(T res, HttpExchange exchange) throws IOException {
        //  JSONify the response object
        String jsonRes = JSONUtilities.generateResponseJSON(res);
        logger.info("Writing: "+jsonRes);
        //  Write the JSON to the response body
        OutputStream responseBody = exchange.getResponseBody();
        writeStringToOutputStream(jsonRes, responseBody);
    }

    public static void writeStringToOutputStream(String str, OutputStream os) throws IOException {

        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }

    public static void writeFileToOutputStream(File file, OutputStream os) throws IOException {
        Files.copy(file.toPath(), os);
    }

    public static void sendErrorBody(Exception ex, HttpExchange exchange) throws IOException{
        //  Anything wrong with the request bubbles here
        Response res = new Response(ex.getMessage(),false);
        ExchangeUtilities.writeResponseToHttpExchange(res,exchange);
    }

    public static <T> T generateRequest(HttpExchange exchange, Class<T> tClass) {
        T tObj = JSONUtilities.createRequestInstance(exchange.getRequestBody(),tClass);
        if (tObj == null) logger.severe("Null object here");
        return tObj;
    }


}
