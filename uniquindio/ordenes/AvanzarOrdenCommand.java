package uniquindio.ordenes;

import uniquindio.coordinacion.Comando;
import uniquindio.notificaciones.EventoOrden;
import uniquindio.notificaciones.PublicadorEventos;

import java.util.Objects;

public class AvanzarOrdenCommand implements Comando {
    private final RepositorioOrdenes repositorio;
    private final int numeroOrden;
    private final PublicadorEventos publicador;

    public AvanzarOrdenCommand(
            RepositorioOrdenes repositorio,
            int numeroOrden,
            PublicadorEventos publicador) {

        this.repositorio = Objects.requireNonNull(repositorio);
        this.numeroOrden = numeroOrden;
        this.publicador = Objects.requireNonNull(publicador);
    }

    @Override
    public void ejecutar() {

        OrdenServicio orden = repositorio
                .buscarPorNumero(numeroOrden)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe la orden #" + numeroOrden
                        )
                );

        String estadoAnterior = orden.getNombreEstado();

        orden.avanzarEstado();

        repositorio.guardar(orden);

        EventoOrden evento = new EventoOrden(
                orden.getNumero(),
                orden.getVehiculo().getPlaca(),
                orden.getVehiculo()
                        .getPropietario().getTelefono(),
                estadoAnterior,
                orden.getNombreEstado()
        );

        publicador.publicar(evento);
    }
}
