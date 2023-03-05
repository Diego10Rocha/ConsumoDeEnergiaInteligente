package model;

import java.time.LocalDate;

public class ConsumoCliente {
    private String dataMedicao;
    private Double valorConsumido;

    public ConsumoCliente(Double valorConsumido) {
        this.dataMedicao = LocalDate.now().toString();
        this.valorConsumido = valorConsumido;
    }
}
