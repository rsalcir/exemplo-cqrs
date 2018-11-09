package cqrs.exemplo.aplicacao.consulta.base.especificacao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public abstract class EspecificacaoBilateral<T> implements Especificacao<T> {

    private Especificacao<T> especificacaoDaEsquerda;
    private Especificacao<T> especificacaoDaDireita;

    EspecificacaoBilateral(Especificacao<T> especificacaoDaEsquerda, Especificacao<T> especificacaoDaDireita) {
        this.especificacaoDaEsquerda = especificacaoDaEsquerda;
        this.especificacaoDaDireita = especificacaoDaDireita;
    }

    protected Especificacao<T> especificacaoDaDireita() {
        return especificacaoDaDireita;
    }

    protected Especificacao<T> especificacaoDaEsquerda() {
        return especificacaoDaEsquerda;
    }

    public List<Especificacao<T>> obterTodasEspecificacoes() {
        List<Especificacao<T>> especificacoesDaEsquerda = this.obterEspecificacoesAninhadas(especificacaoDaEsquerda);
        List<Especificacao<T>> especificacoesDaDireita = this.obterEspecificacoesAninhadas(especificacaoDaDireita);
        return Stream.concat(especificacoesDaEsquerda.stream(), especificacoesDaDireita.stream()).collect(toList());
    }

    private List<Especificacao<T>> obterEspecificacoesAninhadas(Especificacao<T> especificacao) {
        if (!EspecificacaoBilateral.class.isInstance(especificacao)) {
            return singletonList(especificacao);
        }
        EspecificacaoBilateral especificacaoBilateral = (EspecificacaoBilateral) especificacao;
        List<Especificacao<T>> especificacoesDaEsquerda = this.obterEspecificacoesAninhadas(especificacaoBilateral.especificacaoDaEsquerda());
        List<Especificacao<T>> especificacoesDaDireita = this.obterEspecificacoesAninhadas(especificacaoBilateral.especificacaoDaDireita());
        return Stream.concat(especificacoesDaEsquerda.stream(), especificacoesDaDireita.stream()).collect(toList());
    }

    public Optional<Especificacao<T>> obterEspecificacao(Class<T> classeDaEspecificacao) {
        return this.obterTodasEspecificacoes().stream().filter(classeDaEspecificacao::isInstance).findFirst();
    }
}
