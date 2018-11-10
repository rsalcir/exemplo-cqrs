package cqrs.exemplo.dominio.builder;

import cqrs.exemplo.dominio.ContaBancariaId;
import cqrs.exemplo.dominio.ContaRemovida;

import java.util.UUID;

public class ContaRemovidaBuilder {

    private ContaBancariaId id;

    public ContaRemovidaBuilder() {
        this.id = ContaBancariaId.criar(UUID.randomUUID().toString());
    }

    public ContaRemovidaBuilder comId(ContaBancariaId id) {
        this.id = id;
        return this;
    }

    public ContaRemovida criar() {
        return new ContaRemovida(id);
    }
}
