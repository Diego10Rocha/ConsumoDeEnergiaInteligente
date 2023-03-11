package server;

import enums.HttpMethod;
import server.http.HttpRequest;

public class Request {

    /*private HttpMethod httpMethod;

    private String endpoint;

    private String payload;*/


    public static String makeRequest(String httpMethod, String endpoint, String payload) {
        String request = String.format("%s %s HTTP/1.1 \r\nHost: localhost:8081 \r\n%s", httpMethod, endpoint, payload);
        return request;
    }
}
