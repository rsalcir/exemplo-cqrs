package cqrs.exemplo.porta.adaptador.manipuladores.base;

import cqrs.exemplo.dominio.base.EventoDeDominio;

public interface ManipuladorDeEventoDeDominio<E extends EventoDeDominio> {
	void manipular(E evento);
}
