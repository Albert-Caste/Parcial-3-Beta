package uniquindio.cotizacion;

import java.math.BigDecimal;
import java.util.Objects;

public class CalculadoraPresupuesto {
    private EstrategiaTarifa estrategia;

    public CalculadoraPresupuesto(EstrategiaTarifa estrategia) {
        cambiarEstrategia(estrategia);
    }

    public void cambiarEstrategia(EstrategiaTarifa estrategia) {
        this.estrategia = Objects.requireNonNull(
                estrategia,
                "La estrategia no puede ser nula."
        );
    }

    public BigDecimal calcular(BigDecimal valorBase) {
        return estrategia.calcularTotal(valorBase);
    }

    public String getNombreTarifa() {
        return estrategia.getNombre();
    }
}
