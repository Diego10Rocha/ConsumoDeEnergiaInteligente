package server;

import com.google.gson.Gson;
import exception.Error;
import model.BoletoCliente;
import model.ConsumoCliente;
import dto.ResponseAPI;
import enums.HttpStatus;
import exception.HTTPException;
import server.http.HttpRequest;
import server.http.HttpResponse;
import service.ConsumoService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class Atendimento {

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(8080);
        System.out.println("Porta 8080 aberta!");

        while (true) {
            Socket cliente = servidor.accept();
            UUID uuid = UUID.randomUUID();

            HttpRequest httpRequest = new HttpRequest();
            HttpRequest.getRequest(cliente);

            ResponseAPI responseAPI = redirectToRoute(httpRequest);

            switch (responseAPI.getStatus()) {
                case OK -> {
                    System.out.println("OK!");
                    cliente.getOutputStream().write(HttpResponse.OK(responseAPI.getPayload()).getBytes("UTF-8"));
                }
                case CREATED -> cliente.getOutputStream().write(HttpResponse.Created().getBytes("UTF-8"));
                case BAD_REQUEST ->
                        cliente.getOutputStream().write(HttpResponse.BadRequest(responseAPI.getPayload()).getBytes("UTF-8"));
                case NOT_FOUND ->
                        cliente.getOutputStream().write(HttpResponse.NotFound(responseAPI.getPayload()).getBytes("UTF-8"));
                case NOT_ALLOWED -> cliente.getOutputStream().write(HttpResponse.NotAllowed().getBytes("UTF-8"));
                case INTERNAL_SERVER_ERROR ->
                        cliente.getOutputStream().write(HttpResponse.InternalServerError(responseAPI.getPayload()).getBytes("UTF-8"));
            }
            cliente.close();
        }
    }

    private static ResponseAPI redirectToRoute(HttpRequest httpRequest) {
        try {
            String payload = "";
            switch (httpRequest.endpoint) {
                case "/consumo/acompanhar-consumo":
                    ConsumoCliente consumoCliente = ConsumoService.getConsumoAtual(httpRequest);
                    payload = new Gson().toJson(consumoCliente);
                    return new ResponseAPI(payload, HttpStatus.OK);
                case "/consumo/obter-boleto":
                    BoletoCliente boletoCliente = ConsumoService.getBoleto(httpRequest);
                    payload = new Gson().toJson(boletoCliente);
                    return new ResponseAPI(payload, HttpStatus.OK);
                case "/teste":
                    return new ResponseAPI("{\"message\":\"Servidor socket funcionando!\"}", HttpStatus.OK);
                default:
                    return new ResponseAPI();
            }
        } catch (Error ex) {
            return new ResponseAPI(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (HTTPException ex) {
            return new ResponseAPI(ex.getMessage(), HttpStatus.NOT_ALLOWED);
        } catch (Exception ex) {
            return new ResponseAPI(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
