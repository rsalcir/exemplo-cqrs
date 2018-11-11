package cqrs.exemplo.aplicacao.consulta.builder;

import cqrs.exemplo.dominio.ContaBancariaId;
import cqrs.exemplo.porta.adaptador.repositoriodeconsulta.ContaBancariaConsulta;

import java.math.BigDecimal;
import java.util.UUID;

public class ContaBancariaServicoDeConsultaBuilder {

    private ContaBancariaId id;
    private String cliente;
    private BigDecimal valor;

    public ContaBancariaServicoDeConsultaBuilder() {
        this.id = ContaBancariaId.criar(UUID.randomUUID().toString());
        this.cliente = "Alcir Rodrigues dos Santos Junior";
        this.valor = BigDecimal.valueOf(345.67);
    }

    public ContaBancariaServicoDeConsultaBuilder comId(ContaBancariaId id) {
        this.id = id;
        return this;
    }

    public ContaBancariaServicoDeConsultaBuilder comCliente(String cliente) {
        this.cliente = cliente;
        return this;
    }

    public ContaBancariaServicoDeConsultaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public ContaBancariaConsulta criar() {
        return new ContaBancariaConsulta(id, cliente, valor);
    }
}
