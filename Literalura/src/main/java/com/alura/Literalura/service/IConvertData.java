package com.alura.Literalura.service;

public interface IConvertData {
    <T> T obtainData( String json, Class<T> clazz);
}
