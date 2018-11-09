package cqrs.exemplo.porta.adaptador.persistencia.eventos;

import cqrs.exemplo.dominio.base.IdentificadorUnico;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class LocalDeArmazenamentoDosEventosJpa implements LocalDeArmazenamentoDosEventos {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public LocalDeArmazenamentoDosEventosJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<RegistroDoEvento> obter(IdentificadorUnico id) {
        Query query = sessao().createQuery("SELECT evento FROM RegistroDoEvento evento WHERE evento.id = :id");
        query.setParameter("id", id.id());
        return query.list();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void adicionar(String id, String conteudo, int versao, String nomeDoEvento) {
        RegistroDoEvento registroDoEvento = new RegistroDoEvento(id, conteudo, versao, nomeDoEvento);
        sessao().save(registroDoEvento);
    }

    private Session sessao() {
        return entityManager.unwrap(Session.class);
    }
}
