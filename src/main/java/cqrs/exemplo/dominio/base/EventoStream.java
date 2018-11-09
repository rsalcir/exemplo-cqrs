package cqrs.exemplo.dominio.base;

import java.util.Collections;
import java.util.List;

public class EventoStream {

    private int versao;
    private List<EventoDeDominio> eventos;

    public EventoStream(List<EventoDeDominio> eventos, int versao) {
        this.eventos = eventos;
        this.versao = versao;
    }

    public int versao() {
        return versao;
    }

    public List<EventoDeDominio> eventos() {
        return Collections.unmodifiableList(eventos);
    }
}
