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
            throw new IllegalArgumentException("Não foi possivel criar a conta bancaria.");
        }
        ContaCriada contaCriada = new ContaCriada(id, cliente, valor);
        aplicar(contaCriada);
    }

    public void atualizar(String cliente, BigDecimal valor) {
        if (!estadoAtual.criada() || estadoAtual.removida()) {
            throw new IllegalArgumentException("Não é possivel atualizar a conta bancaria.");
        }
        ContaAtualizada contaAtualizada = new ContaAtualizada(estadoAtual.id(), cliente, valor);
        aplicar(contaAtualizada);
    }

    public void remover() {
        if (!estadoAtual.criada() || estadoAtual.removida()) {
            throw new IllegalArgumentException("Não foi possivel remover a conta bancaria.");
        }
        ContaRemovida contaRemovida = new ContaRemovida(estadoAtual.id());
        aplicar(contaRemovida);
    }

    public List<EventoDeDominio> mudancas() {
        return Collections.unmodifiableList(mudancas);
    }
}
