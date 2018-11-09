package cqrs.exemplo.porta.adaptador.repositoriodeconsulta;

import cqrs.exemplo.aplicacao.consulta.ContaBancariaDTO;
import cqrs.exemplo.aplicacao.consulta.ContaBancariaServicoDeConsulta;
import cqrs.exemplo.aplicacao.consulta.modelo.ContaBancaria;
import cqrs.exemplo.porta.adaptador.persistencia.base.ConsultaPorEspecificacaoJpa;
import org.springframework.stereotype.Repository;

@Repository
public class ContaBancariaServicoDeConsultaConcreto extends ConsultaPorEspecificacaoJpa<ContaBancaria, ContaBancariaDTO> implements ContaBancariaServicoDeConsulta {

    @Override
    protected ContaBancariaDTO criarDto(ContaBancaria contaBancaria) {
        ContaBancariaDTO dto = new ContaBancariaDTO();
        dto.id = contaBancaria.identificador().id();
        dto.cliente = contaBancaria.cliente();
        dto.valor = contaBancaria.valor();
        return dto;
    }
}