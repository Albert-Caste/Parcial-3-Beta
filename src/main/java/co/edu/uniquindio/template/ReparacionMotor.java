package co.edu.uniquindio.template;

import co.edu.uniquindio.model.OrdenReparacion;

public class ReparacionMotor extends ProcesoReparacion {

    public ReparacionMotor(OrdenReparacion orden) {
        super(orden);
    }

    @Override
    protected void realizarDiagnostico() {
        System.out.println("Diagnosticando el sistema del motor.");
    }

    @Override
    protected void elaborarPresupuesto() {
        System.out.println("Calculando costos de reparación del motor.");
    }

    @Override
    protected void realizarReparacion() {
        System.out.println("Reparando componentes internos del motor.");
    }

    @Override
    protected void realizarPruebas() {
        System.out.println("Realizando pruebas de funcionamiento del motor.");
    }
}
