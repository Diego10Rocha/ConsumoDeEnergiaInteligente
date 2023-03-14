package client;

import com.google.gson.Gson;
import dto.MedidorDTO;
import enums.HttpMethod;
import server.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Medidor {
    private static String codigoContrato;

    private static Integer valorConsumido = 0;

    private static Integer taxaConsumo = 0;
    private static Socket cliente;
    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);

        String menu = "================================\n" +
                "\tEscolha uma opcao\t\n" +
                "================================\n" +
                "1- Definir valor de consumo momentaneo\n" +
                "2- Aumentar valor do consumo momentaneo\n" +
                "3- Diminuir valor do consumo momentaneo\n";
        try {
            abrirConexaoComServidor();
        } catch (IOException ignored) {

        }
        String request = "GET /uuid HTTP/1.1 \r\nHost: localhost:8081\r\n";
        cliente.getOutputStream().write(request.getBytes("UTF-8"));

        InputStreamReader isr = new InputStreamReader(cliente.getInputStream());
        BufferedReader reader = new BufferedReader(isr);

        while (!reader.ready());
        String line = reader.readLine();
        if(line.contains("OK")){
            line = reader.readLine();
            codigoContrato = reader.readLine();

        }
        if(line == null)
            System.out.println("Erro ao registrar medidor!!!!");
        System.out.println(codigoContrato);
        try {
            cliente.close();
        } catch (IOException ignored) {

        }

        new Thread(() -> {
            while(true) {
                valorConsumido += taxaConsumo;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        }).start();

        new Thread(() -> {
            while(true) {
                try {
                    abrirConexaoComServidor();
                    MedidorDTO medidorDTO = new MedidorDTO(codigoContrato, valorConsumido);
                    String payload = new Gson().toJson(medidorDTO);
                    String requestPattern = Request.makeRequest(HttpMethod.GET.getDescricao(), "/medicao", payload);
                    cliente.getOutputStream().write(requestPattern.getBytes(StandardCharsets.UTF_8));
                    Thread.sleep(10000);
                } catch (IOException | InterruptedException e) {

                } finally {
                    try {
                        cliente.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }).start();

        while (true) {

            int opcao;
            System.out.println(menu);
            System.out.println(valorConsumido);
            opcao = input.nextInt();

            if(opcao == 1) {
                System.out.println("Defina uma taxa de consumo");
                taxaConsumo = input.nextInt();
                if(taxaConsumo < 0) taxaConsumo = 0;
            } else if(opcao == 2) {
                int aumento;
                System.out.println("Defina um valor a ser acrescido na taxa de consumo");
                aumento = input.nextInt();
                taxaConsumo += aumento;
            } else if(opcao == 3) {
                int diminuir;
                System.out.println("Defina um valor a ser decrescido na taxa de consumo");
                diminuir = input.nextInt();
                taxaConsumo -= diminuir;
                if (taxaConsumo < 0) taxaConsumo = 0;
            } else {
                System.out.println("Opção inválida");
            }
        }

    }

    private static void abrirConexaoComServidor() throws IOException {
        cliente = new Socket("127.0.0.1",8080);
        //System.out.println("O cliente se conectou ao servidor!");
    }
}
