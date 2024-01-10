package com.example.prueba.es.publico.application.exception;

public class ErrorWritingException extends RuntimeException{

    public ErrorWritingException(Exception e){
        super(e);
    }
}
