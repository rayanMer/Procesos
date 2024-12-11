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
            wait(); // si la tienda esta llena espera
        }
        almacenLotes.add(lote);
        System.out.println("Teinda// " + nombre + ": Lote recibido con manifiesto " + manifiesto);
        notifyAll();
    }

    public synchronized Jamon venderJamon(double pesoRequerido) throws InterruptedException {
        while (almacenLotes.isEmpty()) {
            wait(); // si la tienda no tiene lotes espera
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
        return null; //si no encuintra el jamon con el peso que quiere el cliente devuelve null
    }

    public String getNombre() {
        return nombre;
    }
}
