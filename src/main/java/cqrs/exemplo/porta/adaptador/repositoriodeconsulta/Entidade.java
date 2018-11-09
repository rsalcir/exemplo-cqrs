package cqrs.exemplo.porta.adaptador.repositoriodeconsulta;

import cqrs.exemplo.dominio.base.IdentificadorUnico;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class Entidade<T extends IdentificadorUnico> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer identificadorDoRepositorio;

    public abstract T identificador();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entidade) {
            Entidade entidade = (Entidade) obj;
            return Objects.equals(identificador(), entidade.identificador());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador());
    }
}
