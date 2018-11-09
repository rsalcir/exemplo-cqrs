package cqrs.exemplo.aplicacao.comando;

import cqrs.exemplo.aplicacao.comando.base.ServicoDeAplicacao;
import cqrs.exemplo.dominio.ContaBancaria;
import cqrs.exemplo.dominio.ContaBancariaId;
import cqrs.exemplo.dominio.base.ArmazenadorDeEventos;
import cqrs.exemplo.dominio.base.EventoStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class ContaBancariaServico extends ServicoDeAplicacao {

    private final ArmazenadorDeEventos armazenadorDeEventos;

    @Autowired
    public ContaBancariaServico(ArmazenadorDeEventos armazenadorDeEventos) {
        this.armazenadorDeEventos = armazenadorDeEventos;
    }

    private void quando(AdicionarContaBancaria comando) {
        Consumer<ContaBancaria> funcao = (analise) -> analise.criar(comando.id(), comando.cliente(), comando.valor());
        atualizar(comando.id(), funcao);
    }

    private void quando(AtualizarContaBancaria comando) {
        Consumer<ContaBancaria> funcao = (analise) -> analise.atualizar(comando.cliente(), comando.valor());
        atualizar(comando.id(), funcao);
    }

    private void quando(RemoverContaBancaria comando) {
        Consumer<ContaBancaria> funcao = (analise) -> analise.remover();
        atualizar(comando.id(), funcao);
    }

    private void atualizar(ContaBancariaId analiseId, Consumer funcao) {
        EventoStream eventoStream = armazenadorDeEventos.obter(analiseId);
        ContaBancaria analise = new ContaBancaria(eventoStream.eventos());
        funcao.accept(analise);
        armazenadorDeEventos.adicionar(analiseId, eventoStream.versao(), analise.mudancas());
    }
}
