package com.github.youssfbr.tabelafipe.models;

public record DadosDTO(
        String codigo ,
        String nome
) {
    @Override
    public String toString() {
        return "Cód: " + codigo +
                "  Descrição: " + nome ;
    }
}
