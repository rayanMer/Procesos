package org.example;

import java.util.Random;


public class MainimplementacionSynchronize {
    public static void main(String[] args) {
        Testigo testigo = new Testigo();
        Atleta[] atletas = new Atleta[4];
        for (int i = 0; i < 4; i++) {
            atletas[i] = new Atleta(i + 1, testigo);
        }

        for (Atleta atleta : atletas) {
            new Thread(atleta).start();
        }
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
        testigo.esperarTurno(dorsal);
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
    private int turno = 0;
    public synchronized void esperarTurno(int dorsal) {
        while (dorsal != turno) {
            try {
                wait();  // espera hasta que sea su turno
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void pasarTestigo(int dorsal) {
        // pasar el testigo, cambiar el turno
        System.out.println("Atleta " + dorsal + " pasa el testigo.");
        turno = (turno + 1) % 4;  // siguient atleta
        notifyAll();  // avisar a los demás atletas que pueden correr
    }
}
