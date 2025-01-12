package com.alura.Literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertingData implements IConvertData{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtainData(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json,clazz);
        } catch (JsonProcessingException e) {
            System.out.println("Error al procesar JSON: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
