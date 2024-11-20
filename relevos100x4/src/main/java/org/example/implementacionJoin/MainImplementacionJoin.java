package org.example.implementacionJoin;
import java.util.Random;

public class MainImplementacionJoin {
    public static void main(String[] args) {
        Testigo testigo = new Testigo();

        Atleta[] atletas = new Atleta[4];
        for (int i = 0; i < 4; i++) {
            atletas[i] = new Atleta(i + 1, testigo);
        }

        try {
            for (Atleta atleta : atletas) {
                Thread hiloAtleta = new Thread(atleta);
                hiloAtleta.start();
                hiloAtleta.join();  // espero a que el atleta termine antes de iniciar el siguiente
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Carrera por relevos terminada.");
    }
}

class Atleta implements Runnable {
    private final int dorsal;
    private final Testigo testigo;

    public Atleta(int dorsal, Testigo testigo) {
        this.dorsal = dorsal;
        this.testigo = testigo;
    }

    @Override
    public void run() {
        correr();
        testigo.pasarTestigo(dorsal);
    }

    private void correr() {
        System.out.println("Atleta " + dorsal + " empieza a correr.");
        try {
            Thread.sleep(new Random().nextInt(2000) + 9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Atleta " + dorsal + " terminó su carrera.");
    }
}

class Testigo {
    public synchronized void pasarTestigo(int dorsal) {
        System.out.println("Atleta " + dorsal + " pasa el testigo.");
    }
}
