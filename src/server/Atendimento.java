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

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(8080);
        System.out.println("Porta 8080 aberta!");

        while (true) {
            Socket cliente = servidor.accept();
            new AtendimentoThread(cliente).start();

        }
    }

}
