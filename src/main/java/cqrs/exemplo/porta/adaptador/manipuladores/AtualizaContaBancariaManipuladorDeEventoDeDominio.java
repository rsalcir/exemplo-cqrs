package cqrs.exemplo.porta.adaptador.manipuladores;

import cqrs.exemplo.porta.adaptador.repositoriodeconsulta.ContaBancaria;
import cqrs.exemplo.dominio.ContaAtualizada;
import cqrs.exemplo.dominio.ContaBancariaId;
import cqrs.exemplo.porta.adaptador.manipuladores.base.ManipuladorDeEventoDeDominio;
import cqrs.exemplo.porta.adaptador.persistencia.base.RepositorioJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class AtualizaContaBancariaManipuladorDeEventoDeDominio extends RepositorioJpa<ContaBancaria, ContaBancariaId> implements ManipuladorDeEventoDeDominio<ContaAtualizada> {

    @Autowired
    protected AtualizaContaBancariaManipuladorDeEventoDeDominio(EntityManager entityManager) {
        super(entityManager);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void manipular(ContaAtualizada evento) {
        ContaBancaria contaBancaria = super.obter(evento.id());
        contaBancaria.atualizar(evento.cliente(), evento.valor());
        super.atualizar(contaBancaria);
    }
}
