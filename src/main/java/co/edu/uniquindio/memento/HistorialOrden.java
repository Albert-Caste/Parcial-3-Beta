package co.edu.uniquindio.memento;

import co.edu.uniquindio.model.OrdenReparacion;

import java.util.Stack;

public class HistorialOrden {

    private Stack<OrdenMemento> historial;

    public HistorialOrden() {
        historial = new Stack<>();
    }

    // Guardar el estado actual de una orden.
    public void guardarEstado(OrdenReparacion orden) {

        if (orden == null) {
            throw new IllegalArgumentException(
                    "La orden no puede ser nula."
            );
        }

        historial.push(orden.crearMemento());
    }

    // Recuperar el último estado guardado.
    public boolean restaurarUltimoEstado(OrdenReparacion orden) {

        if (orden == null || historial.isEmpty()) {
            return false;
        }

        OrdenMemento memento = historial.pop();

        orden.restaurarMemento(memento);

        return true;
    }

    // Consultar cuántos estados están guardados.
    public int cantidadEstadosGuardados() {
        return historial.size();
    }
}