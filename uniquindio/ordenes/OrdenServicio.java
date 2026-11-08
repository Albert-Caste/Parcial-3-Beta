package uniquindio.ordenes;

import uniquindio.clientes.Vehiculo;
import uniquindio.personal.Mecanico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OrdenServicio {
    private final int numero;
    private final Vehiculo vehiculo;
    private EstadoOrden estado = new EstadoRegistrada();

    private Mecanico mecanico;
    private String diagnostico = "";

    private final List<ItemServicio> servicios =
            new ArrayList<>();

    // null significa que todavía no existe una cotización.
    private BigDecimal presupuesto;
    private String nombreTarifa;

    public OrdenServicio(int numero, Vehiculo vehiculo) {

        if (numero <= 0) {
            throw new IllegalArgumentException(
                    "El número de orden debe ser mayor que cero."
            );
        }

        this.numero = numero;

        this.vehiculo = Objects.requireNonNull(
                vehiculo,
                "El vehículo no puede ser nulo."
        );
    }

    public void asignarMecanico(Mecanico mecanico) {

        estado.validarAsignacion();

        this.mecanico = Objects.requireNonNull(
                mecanico,
                "El mecánico no puede ser nulo."
        );
    }

    public void actualizarDiagnostico(String diagnostico) {

        estado.validarEdicion();

        if (diagnostico == null || diagnostico.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El diagnóstico no puede estar vacío."
            );
        }

        this.diagnostico = diagnostico.trim();
        invalidarCotizacion();
    }

    public void agregarServicio(ItemServicio servicio) {

        estado.validarEdicion();

        servicios.add(Objects.requireNonNull(
                servicio,
                "El servicio no puede ser nulo."
        ));

        invalidarCotizacion();
    }

    public BigDecimal calcularValorBase() {

        BigDecimal total = new BigDecimal("0.00");

        for (ItemServicio servicio : servicios) {
            total = total.add(servicio.getValorBase());
        }

        return total;
    }

    public void registrarCotizacion(
            BigDecimal presupuesto,
            String nombreTarifa) {
        estado.validarEdicion();

        if (servicios.isEmpty()) {
            throw new IllegalStateException(
                    "Debe agregar servicios antes de cotizar."
            );
        }

        if (presupuesto == null || presupuesto.signum() < 0) {
            throw new IllegalArgumentException(
                    "El presupuesto debe ser no nulo y no negativo."
            );
        }

        if (nombreTarifa == null || nombreTarifa.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe indicar la tarifa aplicada."
            );
        }

        this.presupuesto = presupuesto.setScale(
                2, RoundingMode.HALF_UP
        );

        this.nombreTarifa = nombreTarifa.trim();
    }

    private void invalidarCotizacion() {
        presupuesto = null;
        nombreTarifa = null;
    }

    public boolean tieneCotizacion() {
        return presupuesto != null;
    }

    public int getNumero() {
        return numero;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public List<ItemServicio> getServicios() {
        return Collections.unmodifiableList(
                new ArrayList<>(servicios)
        );
    }

    public BigDecimal getPresupuesto() {
        return presupuesto;
    }

    public String getNombreTarifa() {
        return nombreTarifa;
    }

    public String getNombreEstado() {
        return estado.getNombre();
    }

    public void avanzarEstado() {
        estado.avanzar(this);
    }

    void cambiarEstado(EstadoOrden nuevoEstado) {
        this.estado = Objects.requireNonNull(
                nuevoEstado,
                "El estado no puede ser nulo."
        );
    }

    @Override
    public String toString() {

        String responsable = mecanico == null
                ? "Sin asignar"
                : mecanico.getNombre();

        String valor = tieneCotizacion()
                ? "$" + presupuesto
                : "Sin cotizar";

        return "Orden #" + numero
                + " | Placa: " + vehiculo.getPlaca()
                + " | Estado: " + getNombreEstado()
                + " | Mecánico: " + responsable
                + " | Presupuesto: " + valor;
    }
}
