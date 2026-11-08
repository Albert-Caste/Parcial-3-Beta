package co.edu.uniquindio.chain;

import co.edu.uniquindio.model.OrdenReparacion;

public abstract class ValidadorOrden {

    private ValidadorOrden siguiente;

    public void establecerSiguiente(ValidadorOrden siguiente) {
        this.siguiente = siguiente;
    }

    public String validar(OrdenReparacion orden) {

        String resultado = validarActual(orden);

        // Si hay un error, se detiene la cadena.
        if (resultado != null) {
            return resultado;
        }

        // Si existe otro validador, continúa con él.
        if (siguiente != null) {
            return siguiente.validar(orden);
        }

        return "La orden de reparación ha sido validada correctamente.";
    }

    protected abstract String validarActual(OrdenReparacion orden);
}