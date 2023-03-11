package service;

import dto.MedidorDTO;
import exception.Error;
import model.BoletoCliente;
import model.ConsumoCliente;
import enums.HttpMethod;
import exception.HTTPException;
import server.Atendimento;
import server.http.HttpRequest;

import java.util.List;
import java.util.Objects;

public class ConsumoService {
    public static ConsumoCliente getConsumoAtual(HttpRequest httpRequest) throws HTTPException {
        if(!httpRequest.getMethod().equals(HttpMethod.GET.getDescricao()))
            throw new HTTPException();
        List<MedidorDTO> medidorDTO = Atendimento.getMedidores().get(httpRequest.getParams().get("contrato"));
        return new ConsumoCliente(medidorDTO.get(medidorDTO.size()-1).getValorMedicao());
    }

    public static BoletoCliente getBoleto(HttpRequest httpRequest) throws HTTPException, Error {
        if(!httpRequest.getMethod().equals(HttpMethod.GET.getDescricao()))
            throw new HTTPException();
        Objects.requireNonNull(httpRequest.getParams());
        if(httpRequest.getParams().containsKey("contrato"))
            throw new Error("contrato Not Found");
        return new BoletoCliente(httpRequest.getParams().get("contrato"), 100.3);
    }
}
