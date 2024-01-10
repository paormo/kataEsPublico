package com.example.prueba.es.publico.application.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class ExceptionUtils {

    public static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(
            ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    log.error("Exception occured : {}", exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
