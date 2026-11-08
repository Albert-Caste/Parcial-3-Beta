package co.edu.uniquindio.mediator;

import co.edu.uniquindio.model.OrdenReparacion;

public class Facturacion {

    public String generarFactura(OrdenReparacion orden) {

        if (orden == null) {
            return "Error: no se puede generar la factura.";
        }

        return "FACTURA GENERADA\n"
                + "Orden: " + orden.getCodigo() + "\n"
                + "Cliente: " + orden.getCliente().getNombre() + "\n"
                + "Vehículo: " + orden.getVehiculo().getPlaca() + "\n"
                + "Total: $" + orden.getPresupuesto();
    }
}
