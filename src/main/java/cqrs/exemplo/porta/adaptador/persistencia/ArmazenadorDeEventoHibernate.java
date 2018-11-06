package cqrs.exemplo.porta.adaptador.persistencia;

import cqrs.exemplo.comando.dominio.base.ArmazenadorDeEvento;
import cqrs.exemplo.comando.dominio.base.EventoDeDominio;
import cqrs.exemplo.comando.dominio.base.IdentificadorUnico;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ArmazenadorDeEventoHibernate implements ArmazenadorDeEvento {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<EventoDeDominio> obter(IdentificadorUnico id) {
        Query query = sessao().createQuery("SELECT evento FROM Evento evento WHERE evento.agregadoId.id = :id");
        query.setParameter("id", id.id());
        List<Evento> eventos = query.list();
        return eventos.stream().map(evento -> evento.evento()).collect(Collectors.toList());
    }

    @Override
    public void armazenar(List<EventoDeDominio> eventoDeDominios) {
        List<Evento> eventos = eventoDeDominios.stream().map(this::converterEventoDeDominioEmEvento).collect(Collectors.toList());
        eventos.stream().forEach(evento -> sessao().save(evento));
    }

    private Evento converterEventoDeDominioEmEvento(EventoDeDominio eventoDeDominio) {
        return new Evento(eventoDeDominio.identificador(), eventoDeDominio);
    }

    private Session sessao() {
        return entityManager.unwrap(Session.class);
    }
}
