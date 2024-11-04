package com.github.youssfbr.tabelafipe.services;

public interface IConverteDados {
    <T> T obterDados(String json , Class<T> classe);
}
