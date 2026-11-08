package co.edu.uniquindio.mediator;

import co.edu.uniquindio.model.OrdenReparacion;

import java.util.Map;

public interface MediadorTaller {

    String procesarOrden(
            OrdenReparacion orden,
            Map<String, Integer> repuestosNecesarios
    );
}
