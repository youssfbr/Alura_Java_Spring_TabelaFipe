package com.github.youssfbr.tabelafipe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConverteDados implements IConverteDados {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json , Class<T> classe) {
        try {
            return mapper.readValue(json , classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // Outra maneira de obter lista
    @Override
    public <T> List<T> obterLista(String json , Class<T> classe) {

        final CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class , classe);

        try {
            return mapper.readValue(json , lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
