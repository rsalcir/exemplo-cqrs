package cqrs.exemplo.base;

import cqrs.exemplo.dominio.base.NotificadorDeEventoDeDominio;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-teste.properties")
@ComponentScan(basePackages = {"cqrs.exemplo.aplicacao", "cqrs.exemplo.dominio", "cqrs.exemplo.porta","cqrs.exemplo.base"})
@DataJpaTest
@RunWith(SpringRunner.class)
public abstract class TesteDeIntegracao {

    @Before
    public void configuracaoInicial() {
        criarMockParaNotificadorDeEventoDeDominio();
    }

    private void criarMockParaNotificadorDeEventoDeDominio() {
        NotificadorDeEventoDeDominio notificadorDeEventoDeDominio = mock(NotificadorDeEventoDeDominio.class);
        NotificadorDeEventoDeDominio.setNotificadorDeEventoDeDominioCorrente(notificadorDeEventoDeDominio);
    }
}
