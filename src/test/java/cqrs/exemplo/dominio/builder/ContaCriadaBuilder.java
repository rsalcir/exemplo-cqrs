package cqrs.exemplo.dominio.builder;

import cqrs.exemplo.dominio.ContaBancariaId;
import cqrs.exemplo.dominio.ContaCriada;

import java.math.BigDecimal;
import java.util.UUID;

public class ContaCriadaBuilder {

    private ContaBancariaId id;
    private String cliente;
    private BigDecimal valor;

    public ContaCriadaBuilder() {
        this.id = ContaBancariaId.criar(UUID.randomUUID().toString());
        this.cliente = "Alcir";
        this.valor = BigDecimal.valueOf(12345.00);
    }

    public ContaCriadaBuilder comId(ContaBancariaId id) {
        this.id = id;
        return this;
    }

    public ContaCriada criar() {
        return new ContaCriada(id, cliente, valor);
    }
}
