package cqrs.exemplo.aplicacao.consulta.base.especificacao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Especificacao<T> {
    
    Predicate paraCondicao(Root<T> root, CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery);

    default Especificacao<T> and(Especificacao<T> outraEspecificacao) {
        return new AndEspecificacao<>(this, outraEspecificacao);
    }

    default Especificacao<T> or(Especificacao<T> outraEspecificacao) {
        return new OrEspecificacao<>(this, outraEspecificacao);
    }

}