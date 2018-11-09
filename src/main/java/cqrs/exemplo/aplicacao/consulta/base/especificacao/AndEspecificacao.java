package cqrs.exemplo.aplicacao.consulta.base.especificacao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AndEspecificacao<T> extends EspecificacaoBilateral<T> {

    AndEspecificacao(Especificacao<T> especificacaoDaEsquerda, Especificacao<T> especificacaoDaDireita) {
        super(especificacaoDaEsquerda, especificacaoDaDireita);
    }

    @Override
    public Predicate paraCondicao(Root<T> root, CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery) {
        return criteriaBuilder.and(
                especificacaoDaEsquerda().paraCondicao(root, criteriaBuilder, criteriaQuery),
                especificacaoDaDireita().paraCondicao(root, criteriaBuilder, criteriaQuery)
        );
    }
}