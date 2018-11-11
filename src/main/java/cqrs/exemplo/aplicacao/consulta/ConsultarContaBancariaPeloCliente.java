package cqrs.exemplo.aplicacao.consulta;


import cqrs.exemplo.aplicacao.consulta.base.especificacao.Especificacao;
import cqrs.exemplo.porta.adaptador.repositoriodeconsulta.ContaBancariaConsulta;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ConsultarContaBancariaPeloCliente implements Especificacao<ContaBancariaConsulta> {

    private String cliente;

    public ConsultarContaBancariaPeloCliente(String cliente) {
        this.cliente = cliente;
    }

    @Override
    public Predicate paraCondicao(Root<ContaBancariaConsulta> root, CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery) {
        return criteriaBuilder.equal(root.get("cliente"), cliente);
    }
}
