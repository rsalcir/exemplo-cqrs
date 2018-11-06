package cqrs.exemplo.comando.dominio;

import cqrs.exemplo.comando.dominio.base.EventoDeDominio;

public class ContaRemovida extends EventoDeDominio {

    public ContaRemovida(ContaBancariaId id) {
        super(id);
    }
}