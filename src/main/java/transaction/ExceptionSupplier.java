package transaction;

import excepciones.ValidationException;

public interface ExceptionSupplier<T> {

    T get() throws ValidationException;
}
