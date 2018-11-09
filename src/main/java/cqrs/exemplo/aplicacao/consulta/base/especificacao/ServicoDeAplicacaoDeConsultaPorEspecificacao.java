package cqrs.exemplo.aplicacao.consulta.base.especificacao;

import cqrs.exemplo.aplicacao.consulta.base.RetornoDoServicoDeConsulta;

public interface ServicoDeAplicacaoDeConsultaPorEspecificacao<R extends RetornoDoServicoDeConsulta> {
    R executar(Especificacao especificacao);
}