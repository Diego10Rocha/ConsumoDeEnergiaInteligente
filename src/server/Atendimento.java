package server;

import com.google.gson.Gson;
import dto.MedidorDTO;
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
import java.util.*;

public class Atendimento {

    private static UUID uuid;
    private static Map<String, List<MedidorDTO>> medidores = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(8080);
        System.out.println("Porta 8080 aberta!");

        while (true) {
            Socket cliente = servidor.accept();
            new Thread(() -> {
                uuid = UUID.randomUUID();
                //System.out.println(uuid);

                try {
                    HttpRequest httpRequest = HttpRequest.getRequest(cliente);
                    ResponseAPI responseAPI = redirectToRoute(httpRequest);

                    switch (responseAPI.getStatus()) {
                        case OK -> {
                            cliente.getOutputStream().write(HttpResponse.OK(responseAPI.getPayload()).getBytes("UTF-8"));
                        }
                        case CREATED -> cliente.getOutputStream().write(HttpResponse.Created().getBytes("UTF-8"));
                        case BAD_REQUEST -> cliente.getOutputStream().write(HttpResponse.BadRequest(responseAPI.getPayload()).getBytes("UTF-8"));
                        case NOT_FOUND -> cliente.getOutputStream().write(HttpResponse.NotFound(responseAPI.getPayload()).getBytes("UTF-8"));
                        case NOT_ALLOWED -> cliente.getOutputStream().write(HttpResponse.NotAllowed().getBytes("UTF-8"));
                        case INTERNAL_SERVER_ERROR -> cliente.getOutputStream().write(HttpResponse.InternalServerError(responseAPI.getPayload()).getBytes("UTF-8"));
                        default -> cliente.getOutputStream().write(HttpResponse.OK(uuid.toString()).getBytes("UTF-8"));
                    }
                    cliente.close();
                } catch (IOException ignored) {

                }
            }).start();

        }
    }

    private synchronized static ResponseAPI redirectToRoute(HttpRequest httpRequest) {
        try {
            String payload = "";
            Gson gson = new Gson();
            switch (httpRequest.getEndpoint()) {
                case "/consumo/acompanhar-consumo":
                    ConsumoCliente consumoCliente = ConsumoService.getConsumoAtual(httpRequest);
                    payload = gson.toJson(consumoCliente);
                    return new ResponseAPI(payload, HttpStatus.OK);
                case "/consumo/obter-boleto":
                    BoletoCliente boletoCliente = ConsumoService.getBoleto(httpRequest);
                    payload = gson.toJson(boletoCliente);
                    return new ResponseAPI(payload, HttpStatus.OK);
                case "/uuid":
                    payload = uuid.toString();
                    medidores.put(payload, new ArrayList<>());
                    return new ResponseAPI(payload, HttpStatus.OK);
                case "/medicao":
                    String body = httpRequest.getBody();
                    MedidorDTO medidorDTO = gson.fromJson(body, MedidorDTO.class);
                    if(medidorDTO.getCodigoContrato() == null || medidorDTO.getCodigoContrato().trim().isEmpty()){
                        medidorDTO.setCodigoContrato(uuid.toString());
                        medidores.put(medidorDTO.getCodigoContrato(), new ArrayList<>());
                    }

                    List<MedidorDTO> medidicoes = medidores.get(medidorDTO.getCodigoContrato());
                    medidicoes.add(medidorDTO);
                    payload = medidorDTO.getCodigoContrato();
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
            ex.printStackTrace();
            return new ResponseAPI(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void setMedidores(Map<String, List<MedidorDTO>> medidores) {
        Atendimento.medidores = medidores;
    }

    public static Map<String, List<MedidorDTO>> getMedidores() {
        return medidores;
    }
}
