package cqrs.exemplo.aplicacao.consulta.base.especificacao;


import cqrs.exemplo.aplicacao.consulta.modelo.ContaBancaria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EspecificacaoVazia implements Especificacao<ContaBancaria> {

    private EspecificacaoVazia() {
    }

    public static EspecificacaoVazia criar() {
        return new EspecificacaoVazia();
    }

    @Override
    public Predicate paraCondicao(Root<ContaBancaria> root, CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery) {
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
}
