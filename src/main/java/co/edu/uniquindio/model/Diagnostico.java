package co.edu.uniquindio.model;

public class Diagnostico {

    private String descripcion;
    private TipoReparacion tipoReparacion;

    public Diagnostico(String descripcion, TipoReparacion tipoReparacion) {
        this.descripcion = descripcion;
        this.tipoReparacion = tipoReparacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoReparacion getTipoReparacion() {
        return tipoReparacion;
    }
}