package basico;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {

    public static void main(String[] args) {
        int PUERTO = 5000;
        String[] coloresDisponibles = {"ROJO", "AZUL", "VERDE", "AMARILLO", "NARANJA", "MORADO"};
        List<String> secuenciaSecreta = generarSecuenciaAleatoria(coloresDisponibles);

        System.out.println("Servidor mastermind iniciado en el puerto: " + PUERTO);
        System.out.println("Secuencia secreta: " + secuenciaSecreta);

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado.");

                try {
                    ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                    ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                    boolean acertado = false;
                    while (!acertado) {
                        List<String> intentoCliente = (List<String>) entrada.readObject();
                        System.out.println("Intento recibido: " + intentoCliente);

                        int correctos = contarCorrectos(secuenciaSecreta, intentoCliente);
                        int presentes = contarPresentes(secuenciaSecreta, intentoCliente);

                        salida.writeObject(new int[]{correctos, presentes});

                        if (correctos == secuenciaSecreta.size()) {
                            acertado = true;
                        }
                    }
                    System.out.println("El cliente ha adivinado la secuencia.");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cliente.close();
                    System.out.println("Conexión cerrada con el cliente.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> generarSecuenciaAleatoria(String[] colores) {
        //Arrays.asList convierte el array en una lista
        List<String> listaColores = Arrays.asList(colores);
        //.shuffle sirve para organiuzar aleatoriamente
        Collections.shuffle(listaColores);
        return listaColores.subList(0, 4);
    }

    private static int contarCorrectos(List<String> secreto, List<String> intento) {
        int correctos = 0;
        for (int i = 0; i < secreto.size(); i++) {
            if (secreto.get(i).equals(intento.get(i))) {
                correctos++;
            }
        }
        return correctos;
    }

    private static int contarPresentes(List<String> secreto, List<String> intento) {
        int presentes = 0;
        List<String> copiaSecreto = new ArrayList<>(secreto);

        for (int i = 0; i < intento.size(); i++) {
            if (secreto.get(i).equals(intento.get(i))) {
                copiaSecreto.set(i, null); //borro los que estan iguales
            }
        }

        for (String color : intento) {
            if (copiaSecreto.contains(color)) {
                copiaSecreto.remove(color);
                presentes++;
            }
        }
        return presentes;
    }
}
