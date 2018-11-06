package cqrs.exemplo;

import cqrs.exemplo.comando.dominio.base.NotificadorDeEventoDeDominio;
import cqrs.exemplo.consulta.manipuladordeevento.NotificadorDeEventoDeDominioPorSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackageClasses = {Application.class})
public class Application {

    //Configuracao do notificador de evento de dominio
    @Bean
    @Autowired
    public NotificadorDeEventoDeDominio notificadorDeEventoDeDominio(ApplicationContext applicationContext) {
        NotificadorDeEventoDeDominio notificadorDeEventoDeDominio = new NotificadorDeEventoDeDominioPorSpring(applicationContext);
        NotificadorDeEventoDeDominio.setNotificadorDeEventoDeDominioCorrente(notificadorDeEventoDeDominio);
        return NotificadorDeEventoDeDominio.getNotificadorCorrente();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
