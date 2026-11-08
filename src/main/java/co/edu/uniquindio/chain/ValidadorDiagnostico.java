package co.edu.uniquindio.chain;

import co.edu.uniquindio.model.OrdenReparacion;

public class ValidadorDiagnostico extends ValidadorOrden {

    @Override
    protected String validarActual(OrdenReparacion orden) {

        if (orden.getDiagnostico() == null) {
            return "Error: la orden no tiene un diagnóstico registrado.";
        }

        if (orden.getDiagnostico().getDescripcion() == null ||
                orden.getDiagnostico().getDescripcion().isBlank()) {

            return "Error: la descripción del diagnóstico está vacía.";
        }

        return null;
    }
}