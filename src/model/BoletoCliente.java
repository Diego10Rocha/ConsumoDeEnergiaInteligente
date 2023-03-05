package model;

import java.time.LocalDate;

public class BoletoCliente {
    private String contrato;
    private Double consumo;
    private String validade;
    private String mesReferencia;

    public BoletoCliente(String contrato, Double consumo) {
        this.contrato = contrato;
        this.consumo = consumo;
        LocalDate dataAtual = LocalDate.now();
        this.validade =  dataAtual.plusDays(7).toString();
        this.mesReferencia = dataAtual.getMonth().toString();
    }
}
