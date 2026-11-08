package co.edu.uniquindio.template;

import co.edu.uniquindio.model.OrdenReparacion;

public class ReparacionElectrica extends ProcesoReparacion {

    public ReparacionElectrica(OrdenReparacion orden) {
        super(orden);
    }

    @Override
    protected void realizarDiagnostico() {
        System.out.println("Revisando batería, alternador y conexiones eléctricas.");
    }

    @Override
    protected void elaborarPresupuesto() {
        System.out.println("Calculando costos de reparación eléctrica.");
    }

    @Override
    protected void realizarReparacion() {
        System.out.println("Reparando conexiones y componentes eléctricos.");
    }

    @Override
    protected void realizarPruebas() {
        System.out.println("Comprobando el funcionamiento del sistema eléctrico.");
    }
}
