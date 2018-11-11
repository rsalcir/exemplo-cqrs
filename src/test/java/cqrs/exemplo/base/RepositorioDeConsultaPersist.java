package cqrs.exemplo.base;

import cqrs.exemplo.porta.adaptador.repositoriodeconsulta.Entidade;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.Stream;

@Repository
public class RepositorioDeConsultaPersist {

    @PersistenceContext
    private EntityManager entityManager;

    public void adicionar(Entidade... entidades) {
        Stream.of(entidades).forEach(entityManager::persist);
    }
}
