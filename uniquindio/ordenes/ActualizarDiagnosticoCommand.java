package uniquindio.ordenes;

import uniquindio.coordinacion.Comando;

import java.util.Objects;

public class ActualizarDiagnosticoCommand implements Comando {
    private final RepositorioOrdenes repositorio;
    private final int numeroOrden;
    private final String diagnostico;

    public ActualizarDiagnosticoCommand(
            RepositorioOrdenes repositorio,
            int numeroOrden,
            String diagnostico) {

        this.repositorio = repositorio;
        this.numeroOrden = numeroOrden;
        this.diagnostico = diagnostico;
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

        orden.actualizarDiagnostico(diagnostico);
        repositorio.guardar(orden);
    }
}
