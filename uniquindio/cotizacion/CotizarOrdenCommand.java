package uniquindio.cotizacion;

import uniquindio.coordinacion.Comando;
import uniquindio.ordenes.OrdenServicio;
import uniquindio.ordenes.RepositorioOrdenes;

import java.math.BigDecimal;

public class CotizarOrdenCommand implements Comando {
    private final RepositorioOrdenes repositorio;
    private final int numeroOrden;
    private final EstrategiaTarifa estrategia;

    public CotizarOrdenCommand(
            RepositorioOrdenes repositorio,
            int numeroOrden,
            EstrategiaTarifa estrategia) {

        this.repositorio = repositorio;
        this.numeroOrden = numeroOrden;
        this.estrategia = estrategia;
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

        if (orden.getServicios().isEmpty()) {
            throw new IllegalStateException(
                    "Debe agregar servicios antes de cotizar."
            );
        }

        CalculadoraPresupuesto calculadora =
                new CalculadoraPresupuesto(estrategia);

        BigDecimal total = calculadora.calcular(
                orden.calcularValorBase()
        );

        orden.registrarCotizacion(
                total,
                calculadora.getNombreTarifa()
        );

        repositorio.guardar(orden);
    }
}
