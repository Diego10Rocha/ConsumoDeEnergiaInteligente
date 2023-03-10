package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BoletoCliente {
    private String contrato;
    private Integer consumo;
    private String validade;
    private String mesReferencia;

    public BoletoCliente(String contrato, Integer consumo) {
        this.contrato = contrato;
        this.consumo = consumo;
        LocalDateTime dataAtual = LocalDateTime.now();
        this.validade =  dataAtual.plusDays(7).toString();
        this.mesReferencia = dataAtual.getMonth().toString();
    }
}
