package co.edu.uniquindio.memento;

import co.edu.uniquindio.model.Diagnostico;
import co.edu.uniquindio.model.Mecanico;

public class OrdenMemento {

    private final Mecanico mecanico;
    private final Diagnostico diagnostico;
    private final double presupuesto;

    public OrdenMemento(
            Mecanico mecanico,
            Diagnostico diagnostico,
            double presupuesto) {

        this.mecanico = mecanico;
        this.diagnostico = diagnostico;
        this.presupuesto = presupuesto;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public double getPresupuesto() {
        return presupuesto;
    }
}
