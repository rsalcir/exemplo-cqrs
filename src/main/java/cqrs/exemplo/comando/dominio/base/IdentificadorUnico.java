package cqrs.exemplo.comando.dominio.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class IdentificadorUnico {

    @Column(nullable = false)
    private String id;

    protected IdentificadorUnico() {
    }

    public IdentificadorUnico(String id) {
        setId(id);
    }

    public String id() {
        return getId();
    }

    private String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        IdentificadorUnico identificador = (IdentificadorUnico) object;
        return Objects.equals(id, identificador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
