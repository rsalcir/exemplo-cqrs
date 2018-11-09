package cqrs.exemplo.dominio;

import cqrs.exemplo.dominio.base.EventoDeDominio;

public class ContaRemovida implements EventoDeDominio {

    private ContaBancariaId id;

    public ContaRemovida(ContaBancariaId id) {
        this.id = id;
    }

    public ContaBancariaId id() {
        return id;
    }
}