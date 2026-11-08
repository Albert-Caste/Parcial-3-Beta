package co.edu.uniquindio.template;

import co.edu.uniquindio.model.OrdenReparacion;

public class ReparacionFrenos extends ProcesoReparacion {

    public ReparacionFrenos(OrdenReparacion orden) {
        super(orden);
    }

    @Override
    protected void realizarDiagnostico() {
        System.out.println("Revisando discos, pastillas y líquido de frenos.");
    }

    @Override
    protected void elaborarPresupuesto() {
        System.out.println("Calculando costos de reparación del sistema de frenos.");
    }

    @Override
    protected void realizarReparacion() {
        System.out.println("Reemplazando o ajustando componentes de los frenos.");
    }

    @Override
    protected void realizarPruebas() {
        System.out.println("Comprobando el funcionamiento del sistema de frenado.");
    }
}