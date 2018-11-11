package cqrs.exemplo.aplicacao.comando;

import cqrs.exemplo.base.EventoPersist;
import cqrs.exemplo.base.TesteDeIntegracao;
import cqrs.exemplo.dominio.ContaAtualizada;
import cqrs.exemplo.dominio.ContaBancariaId;
import cqrs.exemplo.dominio.ContaCriada;
import cqrs.exemplo.dominio.ContaRemovida;
import cqrs.exemplo.dominio.base.ArmazenadorDeEventos;
import cqrs.exemplo.dominio.base.EventoStream;
import cqrs.exemplo.dominio.builder.ContaCriadaBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ContaBancariaServicoDeComandoTeste extends TesteDeIntegracao {

    @Autowired
    private ContaBancariaServicoDeComando servicoDeComando;

    @Autowired
    private ArmazenadorDeEventos armazenadorDeEventos;

    @Autowired
    private EventoPersist eventoPersist;

    private ContaBancariaId contaId;
    private ContaCriada contaCriada;

    @Before
    public void init() {
        contaId = ContaBancariaId.criar(UUID.randomUUID().toString());
        contaCriada = new ContaCriadaBuilder().comId(contaId).criar();
        eventoPersist.adicionar(contaId, contaCriada);
    }

    @Test
    public void deveAdicionarUmaContaBancariaAoExecutarOComandoAdicionarConta() {
        String cliente = "Alcir Junior";
        BigDecimal valor = BigDecimal.valueOf(715.99);
        AdicionarConta comando = new AdicionarConta(cliente, valor);
        servicoDeComando.executar(comando);

        EventoStream eventoStreamConsultado = armazenadorDeEventos.obter(comando.id());
        ContaCriada eventoDeDominioConsultado = (ContaCriada) eventoStreamConsultado.eventos().get(0);

        assertThat(eventoStreamConsultado.eventos(), hasSize(1));
        assertThat(eventoDeDominioConsultado.id(), is(comando.id()));
        assertThat(eventoDeDominioConsultado.cliente(), is(comando.cliente()));
        assertThat(eventoDeDominioConsultado.valor(), is(comando.valor()));
    }

    @Test
    public void deveAtualizarUmaContaBancariaAoExecutarOComandoAtualizarConta() {
        String novoCliente = "Alcir Jr";
        BigDecimal novoValor = BigDecimal.valueOf(86.75);
        AtualizarConta comando = new AtualizarConta(contaId.id(), novoCliente, novoValor);

        servicoDeComando.executar(comando);
        EventoStream eventoStreamConsultado = armazenadorDeEventos.obter(comando.id());
        ContaAtualizada eventoDeDominioConsultado = (ContaAtualizada) eventoStreamConsultado.eventos().get(1);

        assertThat(eventoStreamConsultado.eventos(), hasSize(2));
        assertThat(eventoDeDominioConsultado.id(), is(comando.id()));
        assertThat(eventoDeDominioConsultado.cliente(), is(comando.cliente()));
        assertThat(eventoDeDominioConsultado.valor(), is(comando.valor()));
    }

    @Test
    public void deveRemoverUmaContaBancariaAoExecutarOComandoRemoverConta() {
        RemoverConta comando = new RemoverConta(contaId.id());

        servicoDeComando.executar(comando);
        EventoStream eventoStreamConsultado = armazenadorDeEventos.obter(comando.id());
        ContaRemovida eventoDeDominioConsultado = (ContaRemovida) eventoStreamConsultado.eventos().get(1);

        assertThat(eventoStreamConsultado.eventos(), hasSize(2));
        assertThat(eventoDeDominioConsultado.id(), is(comando.id()));
    }
}
