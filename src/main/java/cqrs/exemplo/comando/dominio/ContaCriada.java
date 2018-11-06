package cqrs.exemplo.comando.dominio;

import cqrs.exemplo.comando.dominio.base.EventoDeDominio;

import java.math.BigDecimal;

public class ContaCriada extends EventoDeDominio {

    private String cliente;
    private BigDecimal valor;

    public ContaCriada(ContaBancariaId contaBancariaId,
                       String cliente,
                       BigDecimal valor) {
        super(contaBancariaId);
        this.cliente = cliente;
        this.valor = valor;
    }

    public String cliente() {
        return cliente;
    }

    public BigDecimal valor() {
        return valor;
    }
}
