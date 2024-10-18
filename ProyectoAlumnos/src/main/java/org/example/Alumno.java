package org.example;

import java.io.Serializable;

public class Alumno  implements Serializable {
    private String nombre;
    private  String apellidos;
    private   String DNI;
    private  String fechaNac;
    private String notaMedia;

    public Alumno(String nombre, String apellidos, String DNI, String fechaNac, String notaMedia) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.DNI = DNI;
        this.fechaNac = fechaNac;
        this.notaMedia = notaMedia;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", DNI='" + DNI + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", notaMedia='" + notaMedia + '\'' +
                '}';
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(String notaMedia) {
        this.notaMedia = notaMedia;
    }
}
