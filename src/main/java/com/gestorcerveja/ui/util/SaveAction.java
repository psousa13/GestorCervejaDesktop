package com.gestorcerveja.ui.util;

import java.util.Map;

/**
 * Interface funcional para o handler de gravação dos formulários modais.
 * Permite lançar qualquer exceção (incluindo {@link java.sql.SQLException}).
 */
@FunctionalInterface
public interface SaveAction {
    void execute(Map<String, String> values) throws Exception;
}
