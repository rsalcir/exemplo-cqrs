package cqrs.exemplo.dominio.base;

import java.util.List;

public interface ArmazenadorDeEventos {
    EventoStream obter(IdentificadorUnico id);
    void adicionar(IdentificadorUnico id, int versaoAtual, List<EventoDeDominio> eventos);
}
