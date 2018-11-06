package cqrs.exemplo.comando.dominio.base;

import cqrs.exemplo.porta.adaptador.persistencia.AgregadoId;

import java.util.Objects;

public abstract class EventoDeDominio {

    private AgregadoId id;

    protected EventoDeDominio(IdentificadorUnico idDoAgregado) {
        this.id = AgregadoId.criar(idDoAgregado.id());
    }

    public AgregadoId identificador() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        EventoDeDominio evento = (EventoDeDominio) object;
        return Objects.equals(id, evento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
