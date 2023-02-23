package server;

import server.http.HttpRequest;
import server.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Atendimento {

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Porta 12345 aberta!");

        while (true) {
            Socket cliente = servidor.accept();

            HttpRequest httpRequest = HttpRequest.getRequest(cliente);

            redirectToRoute(httpRequest.method, httpRequest.endpoint);

            System.out.println("Metodo:"+ httpRequest.method);

            String payload = "Servidor socket funcionando!";
            cliente.getOutputStream().write(HttpResponse.OK(payload).getBytes("UTF-8"));
            cliente.close();

        }

    }

    private static void redirectToRoute(String method, String endpoint) {
        switch (endpoint){
            case "consumo/monitorar":
                break;
            case "consumo/obter-boleto":
                break;
            case "monitor/definir-consumo":
                break;
            case "monitor/aumentar-consumo":
                break;
            case "monitor/diminuir-consumo":
                break;
            case "monitor/cadastrar-cliente":
                break;
        }
    }
}
