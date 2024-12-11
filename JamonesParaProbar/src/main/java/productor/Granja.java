package productor;

import escenario.Secadero;
import modelo.Jamon;

import java.util.Random;

public class Granja extends Thread {
    private final String nombre;
    //timepo de produccion
    private final int tiempoProduccion;
    private final Secadero secadero;
    private final int maxJamones;
    private final Random random;

    public Granja(String nombre, int tiempoProduccion, Secadero secadero, int maxJamones) {
        this.nombre = nombre;
        this.tiempoProduccion = tiempoProduccion;
        this.secadero = secadero;
        this.maxJamones = maxJamones;
        this.random = new Random();
    }

    @Override
    public void run() {
        for (int i = 0; i < maxJamones; i++) {
            try {
                //timepo de produccion
                Thread.sleep(tiempoProduccion);
                // generar jamon con  peso aleatori
                double peso = 6 + (3 * random.nextDouble());
                Jamon jamon = new Jamon(nombre, peso);
                // añadir jamon al secadero
                secadero.ponerJamon(jamon);
                System.out.println( nombre + " ha producido : " + jamon);
            } catch (InterruptedException e) {
                System.out.println("Granja// " + nombre + " interrumpida.");
                break;
            }
        }
        System.out.println("Granja " + nombre + " ha terminado su producción.");
    }
}
