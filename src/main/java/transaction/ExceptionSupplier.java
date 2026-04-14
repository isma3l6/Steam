package org.alexyivan.transaction;

import org.alexyivan.exception.ValidacionException;

public interface ExceptionSupplier<T> {

    T get() throws ValidacionException;
}
