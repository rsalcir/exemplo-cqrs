package cqrs.exemplo.porta.adaptador.persistencia.eventos;

import cqrs.exemplo.dominio.base.IdentificadorUnico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        Query query = this.entityManager.createQuery("SELECT evento FROM RegistroDoEvento evento WHERE evento.id = :id");
        query.setParameter("id", id.id());
        return query.getResultList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void adicionar(String id, String conteudo, int versao, String nomeDoEvento) {
        RegistroDoEvento registroDoEvento = new RegistroDoEvento(id, conteudo, versao, nomeDoEvento);
        this.entityManager.persist(registroDoEvento);
    }
}
