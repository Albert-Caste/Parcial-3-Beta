package co.edu.uniquindio.model;

public class Mecanico {

    private String nombre;
    private String especialidad;

    public Mecanico(String nombre, String especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }
}