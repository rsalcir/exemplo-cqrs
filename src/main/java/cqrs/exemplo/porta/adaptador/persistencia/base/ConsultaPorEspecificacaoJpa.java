package cqrs.exemplo.porta.adaptador.persistencia.base;

import cqrs.exemplo.aplicacao.consulta.base.RetornoDoServicoDeConsulta;
import cqrs.exemplo.aplicacao.consulta.base.especificacao.Especificacao;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class ConsultaPorEspecificacaoJpa<E, DTO> {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public RetornoDoServicoDeConsulta executar(Especificacao especificacao) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(obterClasseDaEntidade());
        Root<E> from = criteriaQuery.from(obterClasseDaEntidade());

        criteriaQuery.select(from);
        Predicate predicadoDaConsulta = especificacao.paraCondicao(from, criteriaBuilder, criteriaQuery);
        criteriaQuery.where(predicadoDaConsulta);

        List<E> entidades = entityManager.createQuery(criteriaQuery).getResultList();

        List<DTO> dtos = entidades.stream().map(this::criarDto).collect(toList());
        return new RetornoDoServicoDeConsulta<>(dtos);
    }

    private Class<E> obterClasseDaEntidade() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected abstract DTO criarDto(E entidade);
}
