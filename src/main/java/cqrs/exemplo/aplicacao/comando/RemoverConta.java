package cqrs.exemplo.aplicacao.comando;

import cqrs.exemplo.aplicacao.comando.base.Comando;
import cqrs.exemplo.dominio.ContaBancariaId;

import java.util.Objects;

public class RemoverConta implements Comando {

    private ContaBancariaId id;

    public RemoverConta(String id) {
        this.id = ContaBancariaId.criar(id);
    }

    public ContaBancariaId id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoverConta that = (RemoverConta) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
