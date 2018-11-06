package cqrs.exemplo.comando.servico;

import cqrs.exemplo.comando.dominio.ContaBancaria;
import cqrs.exemplo.comando.dominio.base.ArmazenadorDeEvento;
import cqrs.exemplo.comando.dominio.base.EventoDeDominio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContaBancariaServico {

    private ArmazenadorDeEvento armazenadorDeEvento;

    @Autowired
    public ContaBancariaServico(ArmazenadorDeEvento armazenadorDeEvento) {
        this.armazenadorDeEvento = armazenadorDeEvento;
    }

    @Transactional(rollbackOn = Exception.class)
    public void executar(AdicionarContaBancaria comando) {
        List<EventoDeDominio> eventoDeDominios = armazenadorDeEvento.obter(comando.id());
        ContaBancaria conta = new ContaBancaria(eventoDeDominios);
        conta.criar(comando.id(), comando.cliente(), comando.valor());
        armazenadorDeEvento.armazenar(conta.eventos());
    }

    @Transactional(rollbackOn = Exception.class)
    public void executar(AtualizarContaBancaria comando) {
        List<EventoDeDominio> eventoDeDominios = armazenadorDeEvento.obter(comando.id());
        ContaBancaria conta = new ContaBancaria(eventoDeDominios);
        conta.atualizar(comando.cliente(), comando.valor());
        armazenadorDeEvento.armazenar(conta.eventos());
    }

    @Transactional(rollbackOn = Exception.class)
    public void executar(RemoverContaBancaria comando) {
        List<EventoDeDominio> eventoDeDominios = armazenadorDeEvento.obter(comando.id());
        ContaBancaria conta = new ContaBancaria(eventoDeDominios);
        conta.remover();
        armazenadorDeEvento.armazenar(conta.eventos());
    }
}
