package escenario;

import modelo.Jamon;

import java.util.ArrayList;
import java.util.List;

public class Secadero {
    private final int capacidadMaxima;
    private final List<Jamon> almacen;

    public Secadero(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.almacen = new ArrayList<>();
    }

    public synchronized void ponerJamon(Jamon jamon) throws InterruptedException {
        while (almacen.size() >= capacidadMaxima) {
            wait(); // escpera si el secadero esta lleno
        }
        almacen.add(jamon);
        System.out.println("Secadero// Jamón añadido: " + jamon);
        notifyAll(); //notifica que el jamon ya se a añadido
    }

    public synchronized Jamon sacarJamon() throws InterruptedException {
        while (almacen.isEmpty()) {
            wait(); // espera si el secadero esta vacío
        }
        //saca el primer jamon
        Jamon jamon = almacen.remove(0);
        System.out.println("Secadero// Jamon extraído: " + jamon);
        notifyAll(); //notifia que se a sacado un jamon ya
        return jamon;
    }

    public synchronized int getCantidadActual() {
        return almacen.size();
    }
}
