package uniquindio.ordenes;

public class EstadoPendienteAprobacion implements EstadoOrden {
    @Override
    public String getNombre() {
        return "PENDIENTE_APROBACION";
    }

    @Override
    public void avanzar(OrdenServicio orden) {
        orden.cambiarEstado(new EstadoEnReparacion());
    }
}
