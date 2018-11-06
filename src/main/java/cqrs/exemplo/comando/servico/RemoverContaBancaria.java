package cqrs.exemplo.comando.servico;

import cqrs.exemplo.comando.dominio.ContaBancariaId;

import java.util.Objects;

public class RemoverContaBancaria {

    private ContaBancariaId id;

    public RemoverContaBancaria(String id) {
        this.id = ContaBancariaId.criar(id);
    }

    public ContaBancariaId id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoverContaBancaria that = (RemoverContaBancaria) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
