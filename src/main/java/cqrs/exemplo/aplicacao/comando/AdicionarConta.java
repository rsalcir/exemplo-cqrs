package cqrs.exemplo.aplicacao.comando;

import cqrs.exemplo.aplicacao.comando.base.Comando;
import cqrs.exemplo.dominio.ContaBancariaId;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class AdicionarConta implements Comando {

    private ContaBancariaId id;
    private String cliente;
    private BigDecimal valor;

    public AdicionarConta(String cliente, BigDecimal valor) {
        this.id = ContaBancariaId.criar(UUID.randomUUID().toString());
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AdicionarConta comando = (AdicionarConta) object;
        return Objects.equals(id, comando.id) &&
                Objects.equals(cliente, comando.cliente) &&
                Objects.equals(valor, comando.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, valor);
    }
}
