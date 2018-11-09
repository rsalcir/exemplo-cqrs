package cqrs.exemplo.dominio;

import cqrs.exemplo.dominio.base.EventoDeDominio;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

public class ContaBancariaEstado {

    private ContaBancariaId id;
    private String cliente;
    private BigDecimal valor;
    private boolean criada;
    private boolean removida;

    public ContaBancariaEstado(List<EventoDeDominio> eventoDeDominios) {
        eventoDeDominios.stream().forEach(this::atualizar);
    }

    public void atualizar(EventoDeDominio evento) {
        try {
            Class<?> classeDoEvento = evento.getClass();
            Method metodoQueSeraExecutado = ContaBancariaEstado.class.getDeclaredMethod("quando", classeDoEvento);
            metodoQueSeraExecutado.invoke(this, evento);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void quando(ContaCriada contaCriada) {
        this.id = contaCriada.id();
        this.cliente = contaCriada.cliente();
        this.valor = contaCriada.valor();
        this.criada = Boolean.TRUE;
    }

    private void quando(ContaAtualizada contaAtualizada) {
        this.cliente = contaAtualizada.cliente();
        this.valor = contaAtualizada.valor();
    }

    private void quando(ContaRemovida contaRemovida) {
        this.cliente = null;
        this.valor = null;
        this.removida = Boolean.TRUE;
    }

    public boolean criada() {
        return criada;
    }

    public boolean removida() {
        return removida;
    }

    public ContaBancariaId id() {
        return id;
    }
}
