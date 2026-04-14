package org.alexyivan.transaction;

import java.util.Optional;

import org.alexyivan.exception.ValidacionException;

/**
 * Implementación no-op de {@link ITransactionManager}.
 * Se usa con repositorios en memoria donde no existe el concepto de
 * transacción.
 */
public class NoOpTransactionManager implements ITransactionManager {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T inTransaction(ExceptionSupplier<T> work) throws ValidacionException {
        try {
            return work.get();
        }catch(ValidacionException e){
            throw e;
        } 
        catch (Exception e) {
            try {
                return (T) Optional.empty();
            } catch (ClassCastException ex) {
                return null;
            }
        }
    }
}
