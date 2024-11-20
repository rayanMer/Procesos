package org.example;

import java.util.Random;
//Runnable porque son hilos
public class Atleta implements Runnable {
    private final int dorsal;
    private final Carrera carrera;

    public Atleta(int dorsal, Carrera carrera) {
        this.dorsal = dorsal;
        this.carrera = carrera;
    }

    @Override
    public void run() {
        carrera.esperarPistoletazo();

        long tiempoInicio = System.currentTimeMillis();
        try {
            Thread.sleep(new Random().nextInt(2000) + 9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long tiempoFin = System.currentTimeMillis();

        carrera.notificarLlegada(dorsal, tiempoFin - tiempoInicio);
    }
}