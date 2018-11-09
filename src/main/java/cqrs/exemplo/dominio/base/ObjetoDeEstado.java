package cqrs.exemplo.dominio.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ObjetoDeEstado {

    public void atualizar(EventoDeDominio eventoDeDominio) {
        try {
            Class<?> classeDoComando = eventoDeDominio.getClass();
            Class<?> classeDoServicoDeAplicacao = Class.forName(this.getClass().getName());
            Method metodoQueSeraExecutado = classeDoServicoDeAplicacao.getDeclaredMethod("quando", classeDoComando);
            metodoQueSeraExecutado.setAccessible(true);
            metodoQueSeraExecutado.invoke(this, eventoDeDominio);
            metodoQueSeraExecutado.setAccessible(false);
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
            throw new IllegalArgumentException("Ocorreu um erro ao executar o evento de dominio.", e);
        }
    }
}
