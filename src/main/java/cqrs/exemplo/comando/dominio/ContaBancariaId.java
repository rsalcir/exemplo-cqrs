package cqrs.exemplo.comando.dominio;

import cqrs.exemplo.comando.dominio.base.IdentificadorUnico;

import javax.persistence.Embeddable;

@Embeddable
public class ContaBancariaId extends IdentificadorUnico {

    private ContaBancariaId() {
    }

    private ContaBancariaId(String id) {
        super(id);
    }

    public static ContaBancariaId criar(String id) {
        return new ContaBancariaId(id);
    }
}
