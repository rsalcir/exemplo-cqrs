package cqrs.exemplo.aplicacao.consulta;

import cqrs.exemplo.aplicacao.consulta.base.RetornoDoServicoDeConsulta;
import cqrs.exemplo.aplicacao.consulta.base.especificacao.Especificacao;
import cqrs.exemplo.aplicacao.consulta.base.especificacao.EspecificacaoVazia;
import cqrs.exemplo.aplicacao.consulta.builder.ContaBancariaServicoDeConsultaBuilder;
import cqrs.exemplo.base.RepositorioDeConsultaPersist;
import cqrs.exemplo.base.TesteDeIntegracao;
import cqrs.exemplo.dominio.ContaBancariaId;
import cqrs.exemplo.porta.adaptador.repositoriodeconsulta.ContaBancariaConsulta;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ContaBancariaServicoDeConsultaTeste extends TesteDeIntegracao {

    @Autowired
    private RepositorioDeConsultaPersist repositorioDeConsultaPersist;

    @Autowired
    private ContaBancariaServicoDeConsulta servicoDeConsulta;

    @Test
    public void deveConsultarTodasAsContasBancarias() {
        String clienteJunior = "Junior";
        ContaBancariaConsulta contaBancariaDoJunior = new ContaBancariaServicoDeConsultaBuilder().comCliente(clienteJunior).criar();
        String clienteAlcir = "Alcir";
        ContaBancariaConsulta contaBancariaDoAlcir = new ContaBancariaServicoDeConsultaBuilder().comCliente(clienteAlcir).criar();
        repositorioDeConsultaPersist.adicionar(contaBancariaDoJunior, contaBancariaDoAlcir);

        Especificacao especificacao = EspecificacaoVazia.criar();
        RetornoDoServicoDeConsulta<ContaBancariaDTO> retornoDaConsulta = servicoDeConsulta.executar(especificacao);
        List<ContaBancariaDTO> contasBancariasConsultada = retornoDaConsulta.valores();

        assertThat(retornoDaConsulta.valores(), hasSize(2));
        assertTrue(contasBancariasConsultada.stream().anyMatch(conta -> conta.cliente.equals(clienteAlcir)));
        assertTrue(contasBancariasConsultada.stream().anyMatch(conta -> conta.cliente.equals(clienteJunior)));
    }

    @Test
    public void deveConsultarAContaBancariaPeloId() {
        ContaBancariaId id = ContaBancariaId.criar(UUID.randomUUID().toString());
        ContaBancariaConsulta contaBancaria = new ContaBancariaServicoDeConsultaBuilder().comId(id).criar();
        repositorioDeConsultaPersist.adicionar(contaBancaria);

        Especificacao especificacao = new ConsultarContaBancariaPeloId(id.id());
        RetornoDoServicoDeConsulta<ContaBancariaDTO> retornoDaConsulta = servicoDeConsulta.executar(especificacao);
        ContaBancariaDTO contasBancariasConsultada = retornoDaConsulta.valor();

        assertThat(retornoDaConsulta.valores(), hasSize(1));
        assertThat(contasBancariasConsultada.id, is(contaBancaria.identificador().id()));
        assertThat(contasBancariasConsultada.cliente, is(contaBancaria.cliente()));
        assertThat(contasBancariasConsultada.valor, is(contaBancaria.valor()));
    }

    @Test
    public void deveConsultarAContaBancariaPeloCliente() {
        String cliente = "Rodrigues";
        ContaBancariaConsulta contaBancaria = new ContaBancariaServicoDeConsultaBuilder().comCliente(cliente).criar();
        repositorioDeConsultaPersist.adicionar(contaBancaria);

        Especificacao especificacao = new ConsultarContaBancariaPeloCliente(cliente);
        RetornoDoServicoDeConsulta<ContaBancariaDTO> retornoDaConsulta = servicoDeConsulta.executar(especificacao);
        ContaBancariaDTO contasBancariasConsultada = retornoDaConsulta.valor();

        assertThat(retornoDaConsulta.valores(), hasSize(1));
        assertThat(contasBancariasConsultada.id, is(contaBancaria.identificador().id()));
        assertThat(contasBancariasConsultada.cliente, is(contaBancaria.cliente()));
        assertThat(contasBancariasConsultada.valor, is(contaBancaria.valor()));
    }
}
