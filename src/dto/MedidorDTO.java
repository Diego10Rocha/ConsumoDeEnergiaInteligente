package dto;

import java.time.LocalDate;

public class MedidorDTO {

    private String codigoContrato;

    private Integer valorMedicao;

    private String dataHoraMedicao;

    public MedidorDTO(String codigoContrato, Integer valorMedicao) {
        this.codigoContrato = codigoContrato;
        this.valorMedicao = valorMedicao;
        this.dataHoraMedicao = LocalDate.now().toString();
    }

    public String getCodigoContrato() {
        return codigoContrato;
    }

    public void setCodigoContrato(String codigoContrato) {
        this.codigoContrato = codigoContrato;
    }

    public Integer getValorMedicao() {
        return valorMedicao;
    }

    public void setValorMedicao(Integer valorMedicao) {
        this.valorMedicao = valorMedicao;
    }

    public String getDataHoraMedicao() {
        return dataHoraMedicao;
    }

    public void setDataHoraMedicao(String dataHoraMedicao) {
        this.dataHoraMedicao = dataHoraMedicao;
    }
}
