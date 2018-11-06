package cqrs.exemplo.porta.adaptador.persistencia;

import cqrs.exemplo.comando.dominio.base.IdentificadorUnico;

import javax.persistence.Embeddable;

@Embeddable
public class EventoId extends IdentificadorUnico {

    private EventoId() {
    }

    private EventoId(String id) {
        super(id);
    }

    public static EventoId criar(String id) {
        return new EventoId(id);
    }
}

