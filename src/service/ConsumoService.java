package service;

import exception.Error;
import model.BoletoCliente;
import model.ConsumoCliente;
import enums.HttpMethod;
import exception.HTTPException;
import server.http.HttpRequest;

import java.util.Objects;

public class ConsumoService {
    public static ConsumoCliente getConsumoAtual(HttpRequest httpRequest) throws HTTPException {
        if(!httpRequest.method.equals(HttpMethod.GET.getDescricao()))
            throw new HTTPException();
        return new ConsumoCliente(100.3);
    }

    public static BoletoCliente getBoleto(HttpRequest httpRequest) throws HTTPException, Error {
        if(!httpRequest.method.equals(HttpMethod.GET.getDescricao()))
            throw new HTTPException();
        Objects.requireNonNull(httpRequest.params);
        if(httpRequest.params.containsKey("contrato"))
            throw new Error("contrato Not Found");
        return new BoletoCliente(httpRequest.params.get("contrato"), 100.3);
    }
}
