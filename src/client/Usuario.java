package client;

import com.google.gson.Gson;
import model.BoletoCliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Usuario {

    public static void main(String[] args) throws IOException {
        Socket cliente = new Socket("127.0.0.1",8080);
        System.out.println("O cliente se conectou ao servidor!");

        PrintStream saida = new PrintStream(cliente.getOutputStream());
        Scanner teclado = new Scanner(System.in);
        /*while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }*/
        BoletoCliente boletoCliente = new BoletoCliente("12332", 100);
        String payload = new Gson().toJson(boletoCliente);
        saida.println(payload);
        saida.close();
        teclado.close();
    }
}
