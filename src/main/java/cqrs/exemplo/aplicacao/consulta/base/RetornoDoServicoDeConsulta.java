package cqrs.exemplo.aplicacao.consulta.base;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

public class RetornoDoServicoDeConsulta<V>  {

    private List<V> valores = new ArrayList<>();

    public RetornoDoServicoDeConsulta() {
    }

    public RetornoDoServicoDeConsulta(List<V> valores) {
        this.valores = valores;
    }

    public RetornoDoServicoDeConsulta(V valor) {
        this.valores = singletonList(valor);
    }

    public List<V> valores() {
        return valores;
    }

    public V valor() {
        return valores.isEmpty() ? null : valores.get(valores.size() - 1);
    }

    public boolean retornouAlgumValor() {
        return valores != null && !valores.isEmpty();
    }
}