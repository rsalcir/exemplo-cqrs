package cqrs.exemplo.aplicacao.consulta;

import cqrs.exemplo.aplicacao.consulta.base.RetornoDoServicoDeConsulta;
import cqrs.exemplo.aplicacao.consulta.base.especificacao.ServicoDeAplicacaoDeConsultaPorEspecificacao;

public interface ContaBancariaServicoDeConsulta extends ServicoDeAplicacaoDeConsultaPorEspecificacao<RetornoDoServicoDeConsulta<ContaBancariaDTO>> {
}
