package uniquindio.notificaciones;

public class NotificadorClienteObserver implements ObservadorOrden{
    private ServicioNotificaciones servicio;

    public NotificadorClienteObserver(ServicioNotificaciones servicio) {
        if (servicio != null) {
            this.servicio = servicio;
        }
    }

    @Override
    public void actualizar(EventoOrden evento) {

        String mensaje =
                "La orden #" + evento.getNumeroOrden()
                        + " de su vehículo " + evento.getPlaca()
                        + " cambió al estado: "
                        + evento.getEstadoNuevo() + ".";

        servicio.enviar(
                evento.getTelefonoCliente(),
                mensaje
        );
    }
}
