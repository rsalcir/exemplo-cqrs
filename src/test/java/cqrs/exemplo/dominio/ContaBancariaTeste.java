package cqrs.exemplo.dominio;

import cqrs.exemplo.dominio.base.EventoDeDominio;
import cqrs.exemplo.dominio.base.NotificadorDeEventoDeDominio;
import cqrs.exemplo.dominio.builder.ContaCriadaBuilder;
import cqrs.exemplo.dominio.builder.ContaRemovidaBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class ContaBancariaTeste {

    @Rule
    public ExpectedException excecaoEsperada = ExpectedException.none();

    private NotificadorDeEventoDeDominio notificadorDeEventoDeDominio;
    private ContaBancariaId contaId;
    private String cliente;
    private BigDecimal valor;

    @Before
    public void init() {
        notificadorDeEventoDeDominio = mock(NotificadorDeEventoDeDominio.class);
        NotificadorDeEventoDeDominio.setNotificadorDeEventoDeDominioCorrente(notificadorDeEventoDeDominio);

        contaId = ContaBancariaId.criar(UUID.randomUUID().toString());
        cliente = "Alcir Jr";
        valor = BigDecimal.valueOf(1000.00);
    }

    @Test
    public void aoCriarUmaContaBancariaDeveNotificarOEventoDeContaCriada() {
        List<EventoDeDominio> eventosOcorridos = Collections.emptyList();
        ContaBancaria contaBancaria = new ContaBancaria(eventosOcorridos);

        ArgumentCaptor<ContaCriada> capturadorDeEvento = ArgumentCaptor.forClass(ContaCriada.class);
        contaBancaria.criar(contaId, cliente, valor);
        Mockito.verify(notificadorDeEventoDeDominio).notificarSobre(capturadorDeEvento.capture());
        ContaCriada eventoCapturado = capturadorDeEvento.getValue();

        assertTrue(contaBancaria.mudancas().stream().anyMatch(evento -> evento instanceof ContaCriada));
        assertThat(eventoCapturado.id(), is(contaId));
        assertThat(eventoCapturado.cliente(), is(cliente));
        assertThat(eventoCapturado.valor(), is(valor));
    }

    @Test
    public void naoDevePermitirCriarAContaCasoAmesmaJaTenhaSidoCriada() {
        excecaoEsperada.expect(IllegalArgumentException.class);
        excecaoEsperada.expectMessage("Não é possivel recriar uma conta bancaria.");

        ContaCriada contaCriada = new ContaCriadaBuilder().comId(contaId).criar();
        List<EventoDeDominio> eventosOcorridos = Collections.singletonList(contaCriada);
        ContaBancaria contaBancaria = new ContaBancaria(eventosOcorridos);
        contaBancaria.criar(contaId, cliente, valor);
    }

    @Test
    public void aoAtualizarAContaBancariaDeveNotificaOEventoDeContaAtualizada() {
        ContaCriada contaCriada = new ContaCriadaBuilder().comId(contaId).criar();
        List<EventoDeDominio> eventosOcorridos = Collections.singletonList(contaCriada);
        ContaBancaria contaBancaria = new ContaBancaria(eventosOcorridos);

        ArgumentCaptor<ContaAtualizada> capturadorDeEvento = ArgumentCaptor.forClass(ContaAtualizada.class);
        BigDecimal novoValor = BigDecimal.valueOf(456.00);
        contaBancaria.atualizar(cliente, novoValor);
        Mockito.verify(notificadorDeEventoDeDominio).notificarSobre(capturadorDeEvento.capture());
        ContaAtualizada eventoCapturado = capturadorDeEvento.getValue();

        assertTrue(contaBancaria.mudancas().stream().anyMatch(evento -> evento instanceof ContaAtualizada));
        assertThat(eventoCapturado.id(), is(contaId));
        assertThat(eventoCapturado.cliente(), is(cliente));
        assertThat(eventoCapturado.valor(), is(novoValor));
    }

    @Test
    public void naoDevePermitirAtualizarAContaCasoAmesmaNaoTenhaSidoCriadaOuRemovida() {
        excecaoEsperada.expect(IllegalArgumentException.class);
        excecaoEsperada.expectMessage("Não é possivel atualizar uma conta bancaria que não foi criada ou que ja foi removida.");

        ContaRemovida contaRemovida = new ContaRemovidaBuilder().comId(contaId).criar();
        List<EventoDeDominio> eventosOcorridos = Collections.singletonList(contaRemovida);
        ContaBancaria contaBancaria = new ContaBancaria(eventosOcorridos);
        contaBancaria.atualizar(cliente, valor);
    }

    @Test
    public void aoRemoverAContaBancariaDeveNotificaOEventoDeContaRemovida() {
        ContaCriada contaCriada = new ContaCriadaBuilder().comId(contaId).criar();
        List<EventoDeDominio> eventosOcorridos = Collections.singletonList(contaCriada);
        ContaBancaria contaBancaria = new ContaBancaria(eventosOcorridos);

        ArgumentCaptor<ContaRemovida> capturadorDeEvento = ArgumentCaptor.forClass(ContaRemovida.class);
        contaBancaria.remover();
        Mockito.verify(notificadorDeEventoDeDominio).notificarSobre(capturadorDeEvento.capture());
        ContaRemovida eventoCapturado = capturadorDeEvento.getValue();

        assertTrue(contaBancaria.mudancas().stream().anyMatch(evento -> evento instanceof ContaRemovida));
        assertThat(eventoCapturado.id(), is(contaId));
    }

    @Test
    public void naoDevePermitirRemoverAContaCasoAmesmaJaTenhaSidoRemovida() {
        excecaoEsperada.expect(IllegalArgumentException.class);
        excecaoEsperada.expectMessage("Não foi possivel remover uma conta bancaria já removida.");

        ContaRemovida contaRemovida = new ContaRemovidaBuilder().comId(contaId).criar();
        List<EventoDeDominio> eventosOcorridos = Collections.singletonList(contaRemovida);
        ContaBancaria contaBancaria = new ContaBancaria(eventosOcorridos);
        contaBancaria.remover();
    }
}
