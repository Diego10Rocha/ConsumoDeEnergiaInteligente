package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Medidor {
    private static String codigoContrato;
    private static Socket cliente;
    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);

        String menu = "================================\n" +
                "\tEscolha uma opcao\t\n" +
                "================================\n" +
                "1- Definir valor de consumo momentaneo\n" +
                "2- Aumentar valor do consumo momentaneo\n" +
                "3- Diminuir valor do consumo momentaneo\n";
        abrirConexaoComServidor();
        //DataInputStream dis = new DataInputStream(cliente.getInputStream());
        //String mensagem = dis.readUTF();
        //cliente.close();
        //dis.close();
        InputStreamReader isr = new InputStreamReader(cliente.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        String line = reader.readLine();
        if(line == null)
            System.out.println("Erro ao registrar medidor!!!!");
        codigoContrato = line;
        System.out.println(codigoContrato);
        while (true) {

            int opcao;
            System.out.println(menu);
            opcao = input.nextInt();

        }

    }

    private static void abrirConexaoComServidor() throws IOException {
        cliente = new Socket("127.0.0.1",8080);
        System.out.println("O cliente se conectou ao servidor!");
    }
}
