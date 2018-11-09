package cqrs.exemplo.porta.adaptador.manipuladores.base;

import cqrs.exemplo.dominio.base.EventoDeDominio;
import cqrs.exemplo.dominio.base.NotificadorDeEventoDeDominio;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NotificadorDeEventoDeDominioPorSpring extends NotificadorDeEventoDeDominio {

	private ApplicationContext applicationContext;

	public NotificadorDeEventoDeDominioPorSpring(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public <E extends EventoDeDominio> void notificarSobre(E eventoDeDominio) {
		String[] beans = applicationContext.getBeanNamesForType(ManipuladorDeEventoDeDominio.class);
		List<Object> manipuladoresDeEventosDeDominio = Stream.of(beans).map(bean -> applicationContext.getBean(bean)).collect(Collectors.toList());
		List<ManipuladorDeEventoDeDominio<E>> manipuladoresQueObservamOEventoDeDominio = buscarOsManipuladoresDoEventoDeDominio(eventoDeDominio, manipuladoresDeEventosDeDominio);
		manipuladoresQueObservamOEventoDeDominio.forEach(manipulador -> manipulador.manipular(eventoDeDominio));
	}

	@SuppressWarnings("unchecked")
	private <E extends EventoDeDominio> List<ManipuladorDeEventoDeDominio<E>> buscarOsManipuladoresDoEventoDeDominio(E eventoDeDominio, List<Object> manipuladoresDeEventoDeDominio) {
		return manipuladoresDeEventoDeDominio.stream()
				.filter(manipulador -> manipuladorDeEventoDeDominioObservaOEvento((ManipuladorDeEventoDeDominio<?>) manipulador, eventoDeDominio))
				.map(manipulador -> (ManipuladorDeEventoDeDominio<E>) manipulador)
				.collect(Collectors.toList());
	}

	private <E extends EventoDeDominio> boolean manipuladorDeEventoDeDominioObservaOEvento(ManipuladorDeEventoDeDominio<?> manipuladorDeEventoDeDominio, E eventoDeDominio) {
		try {
			Class classeDoManipuladorDeEventoDeDominio = manipuladorDeEventoDeDominio.getClass();
			if(objetoEhUmProxyDoFrameworkCglibUsadoPeloSpringParaInjecaoDeDependencia(manipuladorDeEventoDeDominio)) {
				classeDoManipuladorDeEventoDeDominio = Class.forName(manipuladorDeEventoDeDominio.getClass().getGenericSuperclass().getTypeName());
			}
			for(Type interfaceQueOManipuladorImplementa : classeDoManipuladorDeEventoDeDominio.getGenericInterfaces()) {
				if(interfaceQueOManipuladorImplementa instanceof ParameterizedType) {
					return manipuladorDeEventoImplementaOEvento(interfaceQueOManipuladorImplementa, eventoDeDominio);
				}
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Não foi encontrado nenhum manipulador de envento de domínio para o manipuladordeevento: " + eventoDeDominio.getClass().getTypeName());
		}
		return false;
	}

	private <E extends EventoDeDominio> boolean manipuladorDeEventoImplementaOEvento(Type interfaceQueOManipuladorImplementa, E eventoDeDominio) {
		ParameterizedType parameterizedType = (ParameterizedType) interfaceQueOManipuladorImplementa;
		return Stream.of(parameterizedType.getActualTypeArguments())
				.map(tipoDaClasseDoEventoDeDominio -> (Class<?>) tipoDaClasseDoEventoDeDominio)
				.anyMatch(classeDoEventoDeDominio -> classeDoEventoDeDominio.isInstance(eventoDeDominio));
	}

	private boolean objetoEhUmProxyDoFrameworkCglibUsadoPeloSpringParaInjecaoDeDependencia(ManipuladorDeEventoDeDominio<?> manipuladorDeEventoDeDominio) {
		return manipuladorDeEventoDeDominio.getClass().getTypeName().contains("CGLIB");
	}
}
