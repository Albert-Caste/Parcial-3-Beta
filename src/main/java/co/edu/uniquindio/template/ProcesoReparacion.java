package co.edu.uniquindio.template;

import co.edu.uniquindio.model.OrdenReparacion;

public abstract class ProcesoReparacion {

    private OrdenReparacion orden;

    public ProcesoReparacion(OrdenReparacion orden) {
        this.orden = orden;
    }

    // Metodo plantilla: establece el orden del proceso.

    public final void ejecutarProceso() {

        validarOrden();

        realizarDiagnostico();

        elaborarPresupuesto();

        solicitarAutorizacion();

        realizarReparacion();

        realizarPruebas();

        entregarVehiculo();
    }

    // Pasos comunes.
    protected void validarOrden() {
        System.out.println("Validando orden: " + orden.getCodigo());
    }

    protected void solicitarAutorizacion() {
        System.out.println("Solicitando autorización al cliente.");
    }

    protected void entregarVehiculo() {
        System.out.println("Entregando vehículo al cliente.");
    }

    // Pasos que varían según el tipo de reparación.
    protected abstract void realizarDiagnostico();

    protected abstract void elaborarPresupuesto();

    protected abstract void realizarReparacion();

    protected abstract void realizarPruebas();
}
