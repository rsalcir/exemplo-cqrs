package cqrs.exemplo.porta.adaptador.recurso;

import cqrs.exemplo.aplicacao.comando.AdicionarContaBancaria;
import cqrs.exemplo.aplicacao.comando.AtualizarContaBancaria;
import cqrs.exemplo.aplicacao.comando.ContaBancariaServico;
import cqrs.exemplo.aplicacao.comando.RemoverContaBancaria;
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
public class ContaBancariaApi {

    private final ContaBancariaServicoDeConsulta contaBancariaServicoDeConsulta;
    private final ContaBancariaServico contaBancariaServico;

    @Autowired
    public ContaBancariaApi(ContaBancariaServicoDeConsulta contaBancariaServicoDeConsulta,
                            ContaBancariaServico contaBancariaServico) {
        this.contaBancariaServicoDeConsulta = contaBancariaServicoDeConsulta;
        this.contaBancariaServico = contaBancariaServico;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaBancariaDTO> obterPorId(@PathVariable String id) {
        Especificacao especificacao = new ConsultarContaBancariaPeloId(id);
        RetornoDoServicoDeConsulta<ContaBancariaDTO> retornoDoServicoDeConsulta = this.contaBancariaServicoDeConsulta.executar(especificacao);
        return ResponseEntity.ok(retornoDoServicoDeConsulta.valor());
    }

    @GetMapping
    public ResponseEntity<List<ContaBancariaDTO>> obterTodos(@RequestParam(value = "cliente", required = false) String cliente) {
        Especificacao especificacao = montarEspecificacao(cliente);
        RetornoDoServicoDeConsulta<ContaBancariaDTO> retornoDoServicoDeConsulta = this.contaBancariaServicoDeConsulta.executar(especificacao);
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
        AdicionarContaBancaria comando = new AdicionarContaBancaria(httpDTO.cliente, httpDTO.valor);
        contaBancariaServico.executar(comando);
        return CompletableFuture.completedFuture(comando.id().id());
    }

    @PutMapping("/{id}")
    public CompletableFuture<String> atualizar(@PathVariable String id, @RequestBody AtualizarContaBancariaHttpDTO httpDTO) {
        AtualizarContaBancaria comando = new AtualizarContaBancaria(id, httpDTO.cliente, httpDTO.valor);
        contaBancariaServico.executar(comando);
        return CompletableFuture.completedFuture(comando.id().id());
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<String> remover(@PathVariable String id) {
        RemoverContaBancaria comando = new RemoverContaBancaria(id);
        contaBancariaServico.executar(comando);
        return CompletableFuture.completedFuture("");
    }
}
