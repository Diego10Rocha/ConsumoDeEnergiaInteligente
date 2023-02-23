package server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    public HttpRequest(){
        this.params = new HashMap<>();
        this.headers = new HashMap<>();
    }
    public String method;
    public String endpoint;
    public Map<String, String> headers;
    public Map<String, String> params;
    public String body;

    public static HttpRequest getRequest(Socket cliente) throws IOException {
        HttpRequest httpRequest = new HttpRequest();
        InputStreamReader isr = new InputStreamReader(cliente.getInputStream());
        BufferedReader reader = new BufferedReader(isr);

        String line = reader.readLine();
        String[] lineOne = line.split(" ");
        httpRequest.method = lineOne[0];
        String[] endpointAux = lineOne[1].split("\\?");
        httpRequest.endpoint = endpointAux[0];
        for(int i = 1;i < endpointAux.length;i++) {
            String[] param = endpointAux[i].split("=");
            httpRequest.params.put(param[0], param[1]);
        }

        while (line != null && !line.isEmpty()) {
            System.out.println(line);
            line = reader.readLine();
        }

        StringBuilder builder = new StringBuilder();

        while(reader.ready()){
            builder.append((char)reader.read());
        }
        String body = builder.toString();
        body = body.replace("\n", "");
        body = body.replace("\t", "");

        httpRequest.body = body;
        System.out.println(body);
        //isr.close();
        //reader.close();
        return httpRequest;
    }
}
