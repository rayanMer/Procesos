package org.example;
public class Main {
    public static void main(String[] args) {
        Carrera carrera = new Carrera();
        for (int i = 1; i <= 8; i++) {
            Atleta atleta = new Atleta(i, carrera);
            carrera.registrarAtleta(atleta);
        }
        carrera.iniciar();
    }
}


