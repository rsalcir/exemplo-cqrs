package cqrs.exemplo.porta.adaptador.recurso;

import cqrs.exemplo.aplicacao.comando.AdicionarConta;
import cqrs.exemplo.aplicacao.comando.AtualizarConta;
import cqrs.exemplo.aplicacao.comando.ContaBancariaServicoDeComando;
import cqrs.exemplo.aplicacao.comando.RemoverConta;
import cqrs.exemplo.aplicacao.consulta.ConsultarContaBancariaPeloCliente;
import cqrs.exemplo.aplicacao.consulta.ConsultarContaBancariaPeloId;
import cqrs.exemplo.aplicacao.consulta.ContaBancariaDTO;
import cqrs.exemplo.aplicacao.consulta.ContaBancariaServicoDeConsulta;
import cqrs.exemplo.aplicacao.consulta.base.RetornoDoServicoDeConsulta;
import cqrs.exemplo.aplicacao.consulta.base.especificacao.Especificacao;
import cqrs.exemplo.aplicacao.consulta.base.especificacao.EspecificacaoVazia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
@EnableAutoConfiguration
@RequestMapping("/conta")
public class ContaBancariaRecurso {

    private final ContaBancariaServicoDeConsulta servicoDeConsulta;
    private final ContaBancariaServicoDeComando servicoDeComando;

    @Autowired
    public ContaBancariaRecurso(ContaBancariaServicoDeConsulta servicoDeConsulta,
                                ContaBancariaServicoDeComando servicoDeComando) {
        this.servicoDeConsulta = servicoDeConsulta;
        this.servicoDeComando = servicoDeComando;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaBancariaDTO> obterPorId(@PathVariable String id) {
        Especificacao especificacao = new ConsultarContaBancariaPeloId(id);
        RetornoDoServicoDeConsulta<ContaBancariaDTO> retornoDoServicoDeConsulta = this.servicoDeConsulta.executar(especificacao);
        return ResponseEntity.ok(retornoDoServicoDeConsulta.valor());
    }

    @GetMapping
    public ResponseEntity<List<ContaBancariaDTO>> obterTodos(@RequestParam(value = "cliente", required = false) String cliente) {
        Especificacao especificacao = montarEspecificacao(cliente);
        RetornoDoServicoDeConsulta<ContaBancariaDTO> retornoDoServicoDeConsulta = this.servicoDeConsulta.executar(especificacao);
        return ResponseEntity.ok(retornoDoServicoDeConsulta.valores());
    }

    private Especificacao montarEspecificacao(String cliente) {
        Especificacao especificacao = EspecificacaoVazia.criar();
        if (Objects.nonNull(cliente)) {
            especificacao = especificacao.and(new ConsultarContaBancariaPeloCliente(cliente));
        }
        return especificacao;
    }

    @PostMapping
    public CompletableFuture<String> adicionar(@RequestBody AdicionarContaBancariaHttpDTO httpDTO) {
        AdicionarConta comando = new AdicionarConta(httpDTO.cliente, httpDTO.valor);
        servicoDeComando.executar(comando);
        return CompletableFuture.completedFuture(comando.id().id());
    }

    @PutMapping("/{id}")
    public CompletableFuture<String> atualizar(@PathVariable String id, @RequestBody AtualizarContaBancariaHttpDTO httpDTO) {
        AtualizarConta comando = new AtualizarConta(id, httpDTO.cliente, httpDTO.valor);
        servicoDeComando.executar(comando);
        return CompletableFuture.completedFuture(comando.id().id());
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<String> remover(@PathVariable String id) {
        RemoverConta comando = new RemoverConta(id);
        servicoDeComando.executar(comando);
        return CompletableFuture.completedFuture("");
    }
}
