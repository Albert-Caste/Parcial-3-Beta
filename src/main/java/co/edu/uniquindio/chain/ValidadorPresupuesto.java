package co.edu.uniquindio.chain;

import co.edu.uniquindio.model.OrdenReparacion;

public class ValidadorPresupuesto extends ValidadorOrden {

    @Override
    protected String validarActual(OrdenReparacion orden) {

        if (!Double.isFinite(orden.getPresupuesto()) ||
                orden.getPresupuesto() <= 0) {

            return "Error: el presupuesto debe ser mayor que cero.";
        }

        return null;
    }
}
