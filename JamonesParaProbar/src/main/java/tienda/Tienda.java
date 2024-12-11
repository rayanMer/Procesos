package tienda;

import modelo.Jamon;

import java.util.LinkedList;
import java.util.List;

public class Tienda {
    private final String nombre;
    private final List<List<Jamon>> almacenLotes;
    private final int capacidadMaxima;

    public Tienda(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.almacenLotes = new LinkedList<>();
    }

    public synchronized void recibirLote(List<Jamon> lote, String manifiesto) throws InterruptedException {
        while (almacenLotes.size() >= capacidadMaxima) {
            wait(); // Espera si la tienda está llena
        }
        almacenLotes.add(lote);
        System.out.println("Tienda " + nombre + ": Lote recibido con manifiesto " + manifiesto);
        notifyAll();
    }

    public synchronized Jamon venderJamon(double pesoRequerido) throws InterruptedException {
        while (almacenLotes.isEmpty()) {
            wait(); // Espera si no hay lotes
        }
        for (List<Jamon> lote : almacenLotes) {
            for (Jamon jamon : lote) {
                if (Math.abs(jamon.getPeso() - pesoRequerido) <= 0.5) {
                    lote.remove(jamon);
                    if (lote.isEmpty()) {
                        almacenLotes.remove(lote);
                    }
                    notifyAll();
                    return jamon;
                }
            }
        }
        return null; // No se encontró jamón con el peso requerido
    }

    public String getNombre() {
        return nombre;
    }
}
