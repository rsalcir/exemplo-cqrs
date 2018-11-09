package cqrs.exemplo.aplicacao.consulta.modelo;

import cqrs.exemplo.aplicacao.consulta.modelo.base.Entidade;
import cqrs.exemplo.dominio.ContaBancariaId;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class ContaBancaria extends Entidade<ContaBancariaId> {

    private ContaBancariaId id;
    private String cliente;
    private BigDecimal valor;

    private ContaBancaria() {
    }

    public ContaBancaria(ContaBancariaId id, String cliente, BigDecimal valor) {
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
    }

    public void atualizar(String cliente, BigDecimal valor) {
        this.cliente = cliente;
        this.valor = valor;
    }

    @Override
    public ContaBancariaId identificador() {
        return id;
    }

    public String cliente() {
        return cliente;
    }

    public BigDecimal valor() {
        return valor;
    }
}
