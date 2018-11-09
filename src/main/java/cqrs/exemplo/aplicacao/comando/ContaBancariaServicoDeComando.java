package cqrs.exemplo.aplicacao.comando;

import cqrs.exemplo.aplicacao.comando.base.ServicoDeAplicacaoDeComando;
import cqrs.exemplo.dominio.ContaBancaria;
import cqrs.exemplo.dominio.ContaBancariaId;
import cqrs.exemplo.dominio.base.ArmazenadorDeEventos;
import cqrs.exemplo.dominio.base.EventoStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class ContaBancariaServicoDeComando extends ServicoDeAplicacaoDeComando {

    private final ArmazenadorDeEventos armazenadorDeEventos;

    @Autowired
    public ContaBancariaServicoDeComando(ArmazenadorDeEventos armazenadorDeEventos) {
        this.armazenadorDeEventos = armazenadorDeEventos;
    }

    private void quando(AdicionarContaBancaria comando) {
        Consumer<ContaBancaria> funcao = (conta) -> conta.criar(comando.id(), comando.cliente(), comando.valor());
        atualizar(comando.id(), funcao);
    }

    private void quando(AtualizarContaBancaria comando) {
        Consumer<ContaBancaria> funcao = (conta) -> conta.atualizar(comando.cliente(), comando.valor());
        atualizar(comando.id(), funcao);
    }

    private void quando(RemoverContaBancaria comando) {
        Consumer<ContaBancaria> funcao = (conta) -> conta.remover();
        atualizar(comando.id(), funcao);
    }

    private void atualizar(ContaBancariaId id, Consumer funcao) {
        EventoStream eventoStream = armazenadorDeEventos.obter(id);
        ContaBancaria conta = new ContaBancaria(eventoStream.eventos());
        funcao.accept(conta);
        armazenadorDeEventos.adicionar(id, eventoStream.versao(), conta.mudancas());
    }
}
