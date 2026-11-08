package co.edu.uniquindio.mediator;

import co.edu.uniquindio.model.OrdenReparacion;

import java.util.Map;

public class MediadorTallerConcreto implements MediadorTaller {

    private Inventario inventario;
    private Facturacion facturacion;

    public MediadorTallerConcreto(
            Inventario inventario,
            Facturacion facturacion) {

        this.inventario = inventario;
        this.facturacion = facturacion;
    }

    @Override
    public String procesarOrden(
            OrdenReparacion orden,
            Map<String, Integer> repuestosNecesarios) {

        if (orden == null) {
            return "Error: no existe una orden de reparación.";
        }

        // El mediador solicita comprobar los repuestos.
        if (!inventario.hayDisponibilidad(repuestosNecesarios)) {
            return "No se puede procesar la orden: faltan repuestos.";
        }

        // Si hay disponibilidad, se descuentan del inventario.
        inventario.descontarRepuestos(repuestosNecesarios);

        // Finalmente, se solicita generar la factura.
        return facturacion.generarFactura(orden);
    }
}