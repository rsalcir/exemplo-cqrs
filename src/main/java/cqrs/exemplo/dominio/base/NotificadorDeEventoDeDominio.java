package cqrs.exemplo.dominio.base;

public abstract class NotificadorDeEventoDeDominio {

	private static NotificadorDeEventoDeDominio notificadorDeEventoDeDominioCorrente;
	
	public abstract <E extends EventoDeDominio> void notificarSobre(E eventoDeDominio);

	public static NotificadorDeEventoDeDominio getNotificadorCorrente() {
		return notificadorDeEventoDeDominioCorrente;
	}

	public static void setNotificadorDeEventoDeDominioCorrente(NotificadorDeEventoDeDominio notificadorDeEventoDeDominioCorrente) {
		NotificadorDeEventoDeDominio.notificadorDeEventoDeDominioCorrente = notificadorDeEventoDeDominioCorrente;
	}
}
