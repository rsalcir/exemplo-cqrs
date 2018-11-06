package cqrs.exemplo.comando.dominio;

import cqrs.exemplo.comando.dominio.base.EventoDeDominio;

import java.math.BigDecimal;

public class ContaAtualizada extends EventoDeDominio {

    private String cliente;
    private BigDecimal valor;

    public ContaAtualizada(ContaBancariaId id, String cliente, BigDecimal valor) {
        super(id);
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
