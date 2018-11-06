package cqrs.exemplo.comando.dominio;

import cqrs.exemplo.comando.dominio.base.EventoDeDominio;
import cqrs.exemplo.comando.dominio.base.NotificadorDeEventoDeDominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContaBancaria {

    private List<EventoDeDominio> eventoDeDominios = new ArrayList<>();
    private ContaBancariaEstado estadoAtual;

    public ContaBancaria(List<EventoDeDominio> eventoDeDominios) {
        this.estadoAtual = new ContaBancariaEstado(eventoDeDominios);
    }

    public void criar(ContaBancariaId id, String cliente, BigDecimal valor) {
        if (estadoAtual.criada()) {
            throw new IllegalArgumentException("Não foi possivel criar a conta bancaria.");
        }
        ContaCriada contaCriada = new ContaCriada(id, cliente, valor);
        NotificadorDeEventoDeDominio.getNotificadorCorrente().notificarSobre(contaCriada);
        eventoDeDominios.add(contaCriada);
    }

    public void atualizar(String cliente, BigDecimal valor) {
        ContaAtualizada contaAtualizada = new ContaAtualizada(estadoAtual.id(), cliente, valor);
        NotificadorDeEventoDeDominio.getNotificadorCorrente().notificarSobre(contaAtualizada);
        eventoDeDominios.add(contaAtualizada);
    }

    public void remover() {
        ContaRemovida contaRemovida = new ContaRemovida(estadoAtual.id());
        NotificadorDeEventoDeDominio.getNotificadorCorrente().notificarSobre(contaRemovida);
        eventoDeDominios.add(contaRemovida);
    }

    public List<EventoDeDominio> eventos() {
        return Collections.unmodifiableList(eventoDeDominios);
    }
}
