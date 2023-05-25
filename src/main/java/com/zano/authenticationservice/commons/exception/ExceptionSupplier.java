package com.zano.authenticationservice.commons.exception;

import java.util.function.Supplier;

@FunctionalInterface
public interface ExceptionSupplier<T extends Exception> extends Supplier<T> {

}
