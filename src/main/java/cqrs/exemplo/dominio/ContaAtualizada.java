package cqrs.exemplo.dominio;

import cqrs.exemplo.dominio.base.EventoDeDominio;

import java.math.BigDecimal;

public class ContaAtualizada implements EventoDeDominio {

    private ContaBancariaId id;
    private String cliente;
    private BigDecimal valor;

    public ContaAtualizada(ContaBancariaId id, String cliente, BigDecimal valor) {
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
    }

    public ContaBancariaId id() {
        return id;
    }

    public String cliente() {
        return cliente;
    }

    public BigDecimal valor() {
        return valor;
    }
}
