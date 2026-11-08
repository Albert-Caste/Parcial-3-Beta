package uniquindio.ordenes;
import uniquindio.coordinacion.Comando;
import uniquindio.personal.Mecanico;

public class AsignarMecanicoCommand implements Comando {
    private final RepositorioOrdenes repositorio;
    private final int numeroOrden;
    private final Mecanico mecanico;

    public AsignarMecanicoCommand(
            RepositorioOrdenes repositorio,
            int numeroOrden,
            Mecanico mecanico) {

        this.repositorio = repositorio;
        this.numeroOrden = numeroOrden;
        this.mecanico = mecanico;
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

        orden.asignarMecanico(mecanico);
        repositorio.guardar(orden);
    }
}
