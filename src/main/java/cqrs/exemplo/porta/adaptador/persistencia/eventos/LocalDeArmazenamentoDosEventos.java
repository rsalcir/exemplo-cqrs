package cqrs.exemplo.porta.adaptador.persistencia.eventos;

import cqrs.exemplo.dominio.base.IdentificadorUnico;

import java.util.List;

public interface LocalDeArmazenamentoDosEventos {
    List<RegistroDoEvento> obter(IdentificadorUnico id);
    void adicionar(String id, String conteudo, int versao, String nome);
}
