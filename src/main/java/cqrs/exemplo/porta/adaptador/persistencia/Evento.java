package cqrs.exemplo.porta.adaptador.persistencia;

import cqrs.exemplo.comando.dominio.base.EventoDeDominio;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer identificadorDoRepositorio;

    private EventoId id;

    @AttributeOverride(name = "id", column = @Column(name = "agregado_id"))
    private AgregadoId agregadoId;

    @Convert(converter = EventoDeDominioConverter.class)
    @Column(name = "evento", length = 3000)
    private EventoDeDominio evento;

    private String nome;
    private Date dataDeCriacao;

    private Evento() {

    }

    public Evento(AgregadoId agregadoId, EventoDeDominio evento) {
        this.id = EventoId.criar(UUID.randomUUID().toString());
        this.nome = evento.getClass().getName();
        this.evento = evento;
        this.agregadoId = agregadoId;
        this.dataDeCriacao = new Date();
    }

    public EventoDeDominio evento() {
        return evento;
    }
}
