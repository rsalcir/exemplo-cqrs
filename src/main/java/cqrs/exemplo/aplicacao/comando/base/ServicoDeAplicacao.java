package cqrs.exemplo.aplicacao.comando.base;

import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ServicoDeAplicacao {

    @Transactional(rollbackFor = Exception.class)
    public void executar(Comando comando) {
        try {
            Class<?> classeDoComando = comando.getClass();
            Class<?> classeDoServicoDeAplicacao = Class.forName(this.getClass().getName());
            Method metodoQueSeraExecutado = classeDoServicoDeAplicacao.getDeclaredMethod("quando", classeDoComando);
            metodoQueSeraExecutado.setAccessible(true);
            metodoQueSeraExecutado.invoke(this, comando);
            metodoQueSeraExecutado.setAccessible(false);
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
            throw new IllegalArgumentException("Ocorreu um erro ao executar o servico de aplicação.", e);
        }
    }
}
