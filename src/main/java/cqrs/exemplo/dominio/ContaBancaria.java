package cqrs.exemplo.dominio;

import cqrs.exemplo.dominio.base.EventoDeDominio;
import cqrs.exemplo.dominio.base.NotificadorDeEventoDeDominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContaBancaria {

    private List<EventoDeDominio> mudancas = new ArrayList<>();
    private ContaBancariaEstado estadoAtual;

    public ContaBancaria(List<EventoDeDominio> eventos) {
        this.estadoAtual = new ContaBancariaEstado(eventos);
    }

    private void aplicar(EventoDeDominio evento) {
        estadoAtual.atualizar(evento);
        mudancas.add(evento);
        NotificadorDeEventoDeDominio.getNotificadorCorrente().notificarSobre(evento);
    }

    public void criar(ContaBancariaId id, String cliente, BigDecimal valor) {
        if (estadoAtual.criada()) {
            notificarMensagemDeErro("Não é possivel recriar uma conta bancaria.");
        }
        ContaCriada contaCriada = new ContaCriada(id, cliente, valor);
        aplicar(contaCriada);
    }

    public void atualizar(String cliente, BigDecimal valor) {
        if (!estadoAtual.criada() && estadoAtual.removida()) {
            notificarMensagemDeErro("Não é possivel atualizar uma conta bancaria que não foi criada ou que ja foi removida.");
        }
        ContaAtualizada contaAtualizada = new ContaAtualizada(estadoAtual.id(), cliente, valor);
        aplicar(contaAtualizada);
    }

    public void remover() {
        if (!estadoAtual.criada() && estadoAtual.removida()) {
            notificarMensagemDeErro("Não foi possivel remover uma conta bancaria já removida.");
        }
        ContaRemovida contaRemovida = new ContaRemovida(estadoAtual.id());
        aplicar(contaRemovida);
    }

    private void notificarMensagemDeErro(String mensagemDeErro) {
        throw new IllegalArgumentException(mensagemDeErro);
    }

    public List<EventoDeDominio> mudancas() {
        return Collections.unmodifiableList(mudancas);
    }
}
