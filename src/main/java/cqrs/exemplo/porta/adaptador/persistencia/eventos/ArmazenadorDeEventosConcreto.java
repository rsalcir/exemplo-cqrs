package cqrs.exemplo.porta.adaptador.persistencia.eventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import cqrs.exemplo.dominio.base.ArmazenadorDeEventos;
import cqrs.exemplo.dominio.base.EventoDeDominio;
import cqrs.exemplo.dominio.base.EventoStream;
import cqrs.exemplo.dominio.base.IdentificadorUnico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class ArmazenadorDeEventosConcreto implements ArmazenadorDeEventos {

    private LocalDeArmazenamentoDosEventos localDeArmazenamentoDosEventos;

    @Autowired
    public ArmazenadorDeEventosConcreto(LocalDeArmazenamentoDosEventos localDeArmazenamentoDosEventos) {
        this.localDeArmazenamentoDosEventos = localDeArmazenamentoDosEventos;
    }

    @Override
    public EventoStream obter(IdentificadorUnico id) {
        List<RegistroDoEvento> registroDoEvento = localDeArmazenamentoDosEventos.obter(id);
        List<EventoDeDominio> eventos = registroDoEvento.stream().map(registro -> deserializarEvento(registro.conteudo())).collect(Collectors.toList());
        int ultimaVersao = registroDoEvento.stream().map(RegistroDoEvento::versao).max(Comparator.comparing(versao -> versao)).orElse(0);
        return new EventoStream(eventos, ultimaVersao);
    }

    @Override
    public void adicionar(IdentificadorUnico id, int versaoAtual, List<EventoDeDominio> eventos) {
        AtomicInteger versao = new AtomicInteger(versaoAtual + 1);
        eventos.forEach(evento -> localDeArmazenamentoDosEventos.adicionar(
                id.id(),
                serializarEvento(evento),
                versao.getAndIncrement(),
                evento.getClass().getSimpleName()));
    }

    private String serializarEvento(EventoDeDominio evento) {
        String json = new Gson().toJson(evento);
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        jsonObject.addProperty("classe", evento.getClass().getName());
        return jsonObject.toString();
    }

    private EventoDeDominio deserializarEvento(String conteudo) {
        Class<? extends EventoDeDominio> classe = obterClasseDoEventoDeDominio(conteudo);
        return new Gson().fromJson(conteudo, classe);
    }

    private Class<? extends EventoDeDominio> obterClasseDoEventoDeDominio(String conteudo) {
        String classe = new Gson()
                .fromJson(conteudo, JsonObject.class)
                .get("classe")
                .getAsString();
        try {
            return (Class<? extends EventoDeDominio>) ArmazenadorDeEventos.class.getClassLoader().loadClass(classe);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
