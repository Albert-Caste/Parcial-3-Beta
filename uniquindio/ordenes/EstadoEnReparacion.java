package uniquindio.ordenes;

public class EstadoEnReparacion implements EstadoOrden{
        @Override
        public String getNombre() {
            return "EN_REPARACION";
        }

        @Override
        public void avanzar(OrdenServicio orden) {
            orden.cambiarEstado(new EstadoListaEntrega());
        }
}
