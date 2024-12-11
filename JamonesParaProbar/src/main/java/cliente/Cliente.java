package cliente;

import modelo.Jamon;
import tienda.Tienda;

import java.util.List;
import java.util.Random;

public class Cliente extends Thread {
    private final String nombre;
    private final List<Tienda> tiendas;
    private final Random random;

    public Cliente(String nombre, List<Tienda> tiendas) {
        this.nombre = nombre;
        this.tiendas = tiendas;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                double pesoDeseado = 6 + (3 * random.nextDouble());
                System.out.println(nombre + " busca un jamón de " + String.format("%.2f", pesoDeseado) + " kg.");

                boolean jamonEncontrado = false;
                for (Tienda tienda : tiendas) {
                    Jamon jamon = tienda.venderJamon(pesoDeseado);
                    if (jamon != null) {
                        System.out.println(nombre + " compró: " + jamon + " en la tienda " + tienda.getNombre());
                        jamonEncontrado = true;
                        break;
                    }
                }

                if (!jamonEncontrado) {
                    System.out.println(nombre + " no encontró un jamón del peso deseado.");
                }

                Thread.sleep(5000); // Espera 5 segundos antes de buscar otro jamón
            }
        } catch (InterruptedException e) {
            System.out.println(nombre + " interrumpido.");
        }
    }
}
