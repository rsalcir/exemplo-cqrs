package cqrs.exemplo.porta.adaptador.persistencia;

import com.google.gson.Gson;
import cqrs.exemplo.comando.dominio.ContaCriada;
import cqrs.exemplo.comando.dominio.base.EventoDeDominio;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EventoDeDominioConverter implements AttributeConverter<EventoDeDominio, String> {

    @Override
    public String convertToDatabaseColumn(EventoDeDominio dadosDaEscola) {
        Gson gson = new Gson();
        return gson.toJson(dadosDaEscola);
    }

    @Override
    public EventoDeDominio convertToEntityAttribute(String string) {
        Gson gson = new Gson();
        //TODO: obter o cast via reflection
        return gson.fromJson(string, ContaCriada.class);
    }
}
