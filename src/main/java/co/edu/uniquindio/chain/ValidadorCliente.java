package co.edu.uniquindio.chain;

import co.edu.uniquindio.model.OrdenReparacion;

public class ValidadorCliente extends ValidadorOrden {

    @Override
    protected String validarActual(OrdenReparacion orden) {

        if (orden == null) {
            return "Error: la orden de reparación no existe.";
        }

        if (orden.getCliente() == null) {
            return "Error: la orden no tiene un cliente asociado.";
        }

        if (orden.getCliente().getDocumento() == null ||
                orden.getCliente().getDocumento().isBlank()) {

            return "Error: el cliente no tiene un documento válido.";
        }

        return null;
    }
}