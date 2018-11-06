package cqrs.exemplo.porta.adaptador.recurso;

import cqrs.exemplo.comando.servico.AdicionarContaBancaria;
import cqrs.exemplo.comando.servico.AtualizarContaBancaria;
import cqrs.exemplo.comando.servico.ContaBancariaServico;
import cqrs.exemplo.comando.servico.RemoverContaBancaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/conta")
public class ContaBancariaApi {

    private final ContaBancariaServico contaBancariaServico;

    @Autowired
    public ContaBancariaApi(ContaBancariaServico contaBancariaServico) {
        this.contaBancariaServico = contaBancariaServico;
    }

    @PostMapping
    public void adicionar(@RequestBody AdicionarContaBancariaHttpDTO httpDTO) {
        AdicionarContaBancaria comando = new AdicionarContaBancaria(httpDTO.cliente, httpDTO.valor);
        contaBancariaServico.executar(comando);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable String id, @RequestBody AtualizarContaBancariaHttpDTO httpDTO) {
        AtualizarContaBancaria comando = new AtualizarContaBancaria(id, httpDTO.cliente, httpDTO.valor);
        contaBancariaServico.executar(comando);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable String id){
        RemoverContaBancaria comando = new RemoverContaBancaria(id);
        contaBancariaServico.executar(comando);
    }
}
