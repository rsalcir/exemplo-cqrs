package cqrs.exemplo.base;

import cqrs.exemplo.dominio.base.ArmazenadorDeEventos;
import cqrs.exemplo.dominio.base.EventoDeDominio;
import cqrs.exemplo.dominio.base.EventoStream;
import cqrs.exemplo.dominio.base.IdentificadorUnico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Repository
public class EventoPersist {

    private ArmazenadorDeEventos armazenadorDeEventos;

    @Autowired
    public EventoPersist(ArmazenadorDeEventos armazenadorDeEventos) {
        this.armazenadorDeEventos = armazenadorDeEventos;
    }

    @Transactional(rollbackFor = Exception.class)
    public void adicionar(IdentificadorUnico id, EventoDeDominio evento){
        EventoStream eventoStream = armazenadorDeEventos.obter(id);
        armazenadorDeEventos.adicionar(id,eventoStream.versao(), Collections.singletonList(evento));
    }
}
