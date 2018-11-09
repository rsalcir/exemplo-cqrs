package cqrs.exemplo.aplicacao.consulta;

import cqrs.exemplo.aplicacao.consulta.base.especificacao.Especificacao;
import cqrs.exemplo.porta.adaptador.repositoriodeconsulta.ContaBancaria;
import cqrs.exemplo.dominio.ContaBancariaId;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ConsultarContaBancariaPeloId implements Especificacao<ContaBancaria> {

    private ContaBancariaId id;

    public ConsultarContaBancariaPeloId(String id) {
        this.id = ContaBancariaId.criar(id);
    }

    @Override
    public Predicate paraCondicao(Root<ContaBancaria> root, CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery) {
        return criteriaBuilder.equal(root.get("id"), id);
    }
}
