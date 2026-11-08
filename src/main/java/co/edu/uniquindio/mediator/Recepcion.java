package co.edu.uniquindio.mediator;

import co.edu.uniquindio.model.OrdenReparacion;

import java.util.Map;

public class Recepcion {

    private MediadorTaller mediador;

    public Recepcion(MediadorTaller mediador) {
        this.mediador = mediador;
    }

    public String registrarOrden(
            OrdenReparacion orden,
            Map<String, Integer> repuestosNecesarios) {

        return mediador.procesarOrden(
                orden,
                repuestosNecesarios
        );
    }
}