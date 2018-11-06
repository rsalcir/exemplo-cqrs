package cqrs.exemplo.consulta.manipuladordeevento;

import cqrs.exemplo.comando.dominio.base.EventoDeDominio;

public interface ManipuladorDeEventoDeDominio<E extends EventoDeDominio> {
	void manipular(E evento);
}
