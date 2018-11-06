package cqrs.exemplo.comando.dominio.base;

import java.util.List;

public interface ArmazenadorDeEvento {
    List<EventoDeDominio> obter(IdentificadorUnico id);
    void armazenar(List<EventoDeDominio> eventoDeDominios);
}
