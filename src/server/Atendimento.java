package server;

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
            InputStreamReader isr = new InputStreamReader(cliente.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }

            String payload = "Servidor socket funcionando!";
            cliente.getOutputStream() .write(HttpResponse.OK(payload).getBytes("UTF-8"));
            cliente.close();;
            isr.close();
            reader.close();

        }

    }
}
