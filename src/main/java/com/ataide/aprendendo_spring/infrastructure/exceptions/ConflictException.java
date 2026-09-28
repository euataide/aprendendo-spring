package com.ataide.aprendendo_spring.infrastructure.exceptions;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

public class ConflictException extends RuntimeException{

    public ConflictException(String mensagem){
        super(mensagem);
    }

    public ConflictException(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
