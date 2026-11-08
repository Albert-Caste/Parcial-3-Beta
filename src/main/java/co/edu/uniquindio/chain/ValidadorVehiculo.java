package co.edu.uniquindio.chain;

import co.edu.uniquindio.model.OrdenReparacion;

public class ValidadorVehiculo extends ValidadorOrden {

    @Override
    protected String validarActual(OrdenReparacion orden) {

        if (orden.getVehiculo() == null) {
            return "Error: la orden no tiene un vehículo asociado.";
        }

        if (orden.getVehiculo().getPlaca() == null ||
                orden.getVehiculo().getPlaca().isBlank()) {

            return "Error: el vehículo no tiene una placa válida.";
        }

        return null;
    }
}
