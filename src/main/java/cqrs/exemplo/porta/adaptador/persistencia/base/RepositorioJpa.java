package cqrs.exemplo.porta.adaptador.persistencia.base;

import cqrs.exemplo.dominio.base.IdentificadorUnico;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;

@Repository
public class RepositorioJpa<E, I extends IdentificadorUnico> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    protected RepositorioJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void adicionar(E entidade) {
        sessao().save(entidade);
    }

    public void remover(E entidade) {
        sessao().delete(entidade);
    }

    public void atualizar(E entidade) {
        sessao().update(entidade);
    }

    public E obter(I identificadorUnico) {
        Class<E> classe = obterClasseDaEntidade();
        Query query = sessao().createQuery("FROM " + classe.getSimpleName() + " WHERE id.id = :identificador");
        query.setParameter("identificador", identificadorUnico.id());
        return (E) query.uniqueResult();
    }

    private Class<E> obterClasseDaEntidade() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private Session sessao() {
        return entityManager.unwrap(Session.class);
    }
}
