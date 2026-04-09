package br.com.project.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusChamado {
    ABERTO,
    EM_ANDAMENTO,
    CONCLUIDO;

    @JsonCreator
    public static StatusChamado fromString(String value){
        if(value == null){
            return null;
        }

        String statusFormatado = value.trim().toUpperCase().replace(" ","_");

        for(StatusChamado status: StatusChamado.values()){
            if(status.name().equals(statusFormatado)){
                return status;
            }
        }

        throw new IllegalArgumentException("O status '" + value + "' não é um status válido.");
    }
}
