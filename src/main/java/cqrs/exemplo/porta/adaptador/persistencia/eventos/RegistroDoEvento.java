package cqrs.exemplo.porta.adaptador.persistencia.eventos;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RegistroDoEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer identificadorDoRepositorio;

    private String id;

    private String nome;

    private int versao;

    @Column(length = 3000)
    private String conteudo;

    private LocalDateTime data;

    private RegistroDoEvento() {
    }

    public RegistroDoEvento(String id, String conteudo, int versao, String nome) {
        this.id = id;
        this.conteudo = conteudo;
        this.versao = versao;
        this.nome = nome;
        this.data = LocalDateTime.now();
    }

    public String id() {
        return id;
    }

    public String nome() {
        return nome;
    }

    public int versao() {
        return versao;
    }

    public String conteudo() {
        return conteudo;
    }
}
