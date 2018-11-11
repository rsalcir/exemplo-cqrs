package cqrs.exemplo.dominio;

import cqrs.exemplo.dominio.base.EventoDeDominio;
import cqrs.exemplo.dominio.builder.ContaCriadaBuilder;
import cqrs.exemplo.dominio.builder.ContaRemovidaBuilder;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ContaBancariaEstadoTeste {

    @Test
    public void deveAtualizarOEstadoDaContaBancariaConformeOsEventosOcorridos() {
        ContaBancariaId contaId = ContaBancariaId.criar(UUID.randomUUID().toString());
        ContaCriada contaCriada = new ContaCriadaBuilder().comId(contaId).criar();
        ContaRemovida contaRemovida = new ContaRemovidaBuilder().comId(contaId).criar();
        List<EventoDeDominio> eventosOcorridos = Arrays.asList(contaCriada, contaRemovida);

        ContaBancariaEstado estadoAtual = new ContaBancariaEstado(eventosOcorridos);

        assertThat(contaId, is(estadoAtual.id()));
        assertTrue(estadoAtual.criada());
        assertTrue(estadoAtual.removida());
    }
}
