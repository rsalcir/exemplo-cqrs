package cqrs.exemplo.porta.adaptador.persistencia;

import cqrs.exemplo.comando.dominio.base.IdentificadorUnico;

import javax.persistence.Embeddable;

@Embeddable
public class AgregadoId extends IdentificadorUnico {

    private AgregadoId() {
    }

    private AgregadoId(String id) {
        super(id);
    }

    public static AgregadoId criar(String id) {
        return new AgregadoId(id);
    }
}

