package uniquindio.notificaciones;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PublicadorEventos {
    private final List<ObservadorOrden> observadores = new ArrayList<>();

    public void suscribir(ObservadorOrden observador) {
        if(observador==null) {
            System.out.println("El observador no puede ser nulo.");
        };

        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void desuscribir(ObservadorOrden observador) {
        observadores.remove(observador);
    }

    public void publicar(EventoOrden evento) {
        if(evento==null) {
            System.out.println("El evento no puede ser nulo.");
        };

        // La copia permite modificar las suscripciones durante una notificación sin alterar este recorrido.
        for (ObservadorOrden observador :
                new ArrayList<>(observadores)) {

            observador.actualizar(evento);
        }
    }
}
