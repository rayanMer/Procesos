package org.example;

public class Carrera {
    private boolean empezar = false;
    private final Thread[] atletas = new Thread[8];  // Array de hilos
    private int pos = 0;

    public void registrarAtleta(Atleta atleta) {
        if (pos < atletas.length) {
            Thread hiloAtleta = new Thread(atleta);
            atletas[pos++] = hiloAtleta;
        } else {
            throw new IllegalStateException("Se han registrado más atletas de los permitidos.");
        }
    }

    public void iniciar() {
        System.out.println("Preparados...");
        esperar(1000);
        System.out.println("Listos...");
        esperar(1000);

        // Sincronizar y cambiar la bandera empezar
        synchronized (this) {
            System.out.println("¡Ya!");
            empezar = true;
            notifyAll();
        }

        for (Thread atleta : atletas) {
            atleta.start();
        }

        // Esperar que los atletas terminen
        for (Thread atleta : atletas) {
            try {
                atleta.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Carrera terminada.");
    }


    public void notificarLlegada(int dorsal, long tiempo) {
        System.out.println("Atleta " + dorsal + " tarda " + tiempo + " ms.");
    }

    private void esperar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public synchronized void esperarPistoletazo() {
        while (!empezar) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
