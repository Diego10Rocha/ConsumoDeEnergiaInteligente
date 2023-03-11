package model;

import java.time.LocalDate;

public class ConsumoCliente {
    private String dataMedicao;
    private Integer valorConsumido;

    public ConsumoCliente(Integer valorConsumido) {
        this.dataMedicao = LocalDate.now().toString();
        this.valorConsumido = valorConsumido;
    }
}
