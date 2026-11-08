package co.edu.uniquindio.model;


import co.edu.uniquindio.memento.OrdenMemento;

import java.time.LocalDateTime;

public class OrdenReparacion {

    private String codigo;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private Mecanico mecanico;
    private Diagnostico diagnostico;
    private double presupuesto;
    private LocalDateTime fechaCreacion;

    private OrdenReparacion(Builder builder) {
        this.codigo = builder.codigo;
        this.cliente = builder.cliente;
        this.vehiculo = builder.vehiculo;
        this.mecanico = builder.mecanico;
        this.diagnostico = builder.diagnostico;
        this.presupuesto = builder.presupuesto;
        this.fechaCreacion = builder.fechaCreacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public OrdenMemento crearMemento() {

        return new OrdenMemento(
                mecanico,
                diagnostico,
                presupuesto
        );
    }

    // Restaurar los datos guardados anteriormente.
    public void restaurarMemento(OrdenMemento memento) {

        if (memento == null) {
            throw new IllegalArgumentException(
                    "El recuerdo no puede ser nulo."
            );
        }

        this.mecanico = memento.getMecanico();
        this.diagnostico = memento.getDiagnostico();
        this.presupuesto = memento.getPresupuesto();
    }

    // PATRÓN BUILDER
    public static class Builder {

        private String codigo;
        private Cliente cliente;
        private Vehiculo vehiculo;
        private Mecanico mecanico;
        private Diagnostico diagnostico;
        private double presupuesto;
        private LocalDateTime fechaCreacion;

        public Builder(String codigo, Cliente cliente,
                       Vehiculo vehiculo) {
            this.codigo = codigo;
            this.cliente = cliente;
            this.vehiculo = vehiculo;
            this.fechaCreacion = LocalDateTime.now();
        }

        public Builder mecanico(Mecanico mecanico) {
            this.mecanico = mecanico;
            return this;
        }

        public Builder diagnostico(Diagnostico diagnostico) {
            this.diagnostico = diagnostico;
            return this;
        }

        public Builder presupuesto(double presupuesto) {
            this.presupuesto = presupuesto;
            return this;
        }

        public OrdenReparacion build() {
            return new OrdenReparacion(this);
        }
    }
}
