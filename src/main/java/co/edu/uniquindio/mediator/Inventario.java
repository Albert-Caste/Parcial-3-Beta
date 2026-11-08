package co.edu.uniquindio.mediator;

import java.util.HashMap;
import java.util.Map;

public class Inventario {

    private Map<String, Integer> existencias;

    public Inventario() {
        existencias = new HashMap<>();
    }

    public void registrarRepuesto(String nombre, int cantidad) {

        if (nombre == null || nombre.isBlank() || cantidad < 0) {
            throw new IllegalArgumentException(
                    "Los datos del repuesto no son válidos."
            );
        }

        existencias.put(nombre, cantidad);
    }

    public boolean hayDisponibilidad(
            Map<String, Integer> repuestosNecesarios) {

        if (repuestosNecesarios == null) {
            return false;
        }

        for (Map.Entry<String, Integer> repuesto
                : repuestosNecesarios.entrySet()) {

            String nombre = repuesto.getKey();
            Integer cantidad = repuesto.getValue();

            if (nombre == null || cantidad == null || cantidad <= 0) {
                return false;
            }

            int disponible = existencias.getOrDefault(nombre, 0);

            if (disponible < cantidad) {
                return false;
            }
        }

        return true;
    }

    public void descontarRepuestos(
            Map<String, Integer> repuestosNecesarios) {

        if (!hayDisponibilidad(repuestosNecesarios)) {
            throw new IllegalStateException(
                    "No hay suficientes repuestos para realizar el descuento."
            );
        }

        for (Map.Entry<String, Integer> repuesto
                : repuestosNecesarios.entrySet()) {

            String nombre = repuesto.getKey();
            int cantidad = repuesto.getValue();

            existencias.put(
                    nombre,
                    existencias.get(nombre) - cantidad
            );
        }
    }

    public int consultarExistencias(String nombre) {
        return existencias.getOrDefault(nombre, 0);
    }
}