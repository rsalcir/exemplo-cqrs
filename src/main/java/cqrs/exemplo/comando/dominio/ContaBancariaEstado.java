package cqrs.exemplo.comando.dominio;

import cqrs.exemplo.comando.dominio.base.EventoDeDominio;

import java.math.BigDecimal;
import java.util.List;

public class ContaBancariaEstado {

    private ContaBancariaId id;
    private String cliente;
    private BigDecimal valor;
    private boolean criada;

    public ContaBancariaEstado(List<EventoDeDominio> eventoDeDominios) {
        for (EventoDeDominio eventoDeDominio : eventoDeDominios) {
            if (eventoDeDominio instanceof ContaCriada) {
                quando((ContaCriada) eventoDeDominio);
            }
            if (eventoDeDominio instanceof ContaAtualizada) {
                quando((ContaAtualizada) eventoDeDominio);
            }
        }
    }

    private void quando(ContaCriada contaCriada) {
        this.id = ContaBancariaId.criar(contaCriada.identificador().id());
        this.cliente = contaCriada.cliente();
        this.valor = contaCriada.valor();
        this.criada = Boolean.TRUE;
    }

    private void quando(ContaAtualizada contaAtualizada) {
        this.cliente = contaAtualizada.cliente();
        this.valor = contaAtualizada.valor();
    }

    public boolean criada() {
        return criada;
    }

    public ContaBancariaId id() {
        return id;
    }
}
