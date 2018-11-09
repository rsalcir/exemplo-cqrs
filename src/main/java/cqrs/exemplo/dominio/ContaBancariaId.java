package cqrs.exemplo.dominio;

import cqrs.exemplo.dominio.base.IdentificadorUnico;

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
