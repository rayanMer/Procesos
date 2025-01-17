
package basico;

import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int PUERTO = 5000;
        String[] coloresDisponibles = {"ROJO", "AZUL", "VERDE", "AMARILLO", "NARANJA", "MORADO"};

        try {
            InetAddress direccion = InetAddress.getLocalHost();
            Socket servidor = new Socket("172.29.10.3", PUERTO);
            ObjectOutputStream salida = new ObjectOutputStream(servidor.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(servidor.getInputStream());
            System.out.println("Conexion establecida con el servidor.");
            System.out.println("Colores disponibles: " + Arrays.toString(coloresDisponibles));

            boolean acertado = false;
            while (!acertado) {
                System.out.println("Introduce tu intento *4 colores separados por espacios*:");
                String[] intento = scanner.nextLine().toUpperCase().split(" ");

                // verificar que hay 4 colores escritos
                if (intento.length != 4) {
                    System.out.println("Error: Debes introducir exactamente 4 colores.");
                    continue; // reintentar
                }

                List<String> listaIntento = Arrays.asList(intento);
                salida.writeObject(listaIntento);


                int[] respuesta = (int[]) entrada.readObject();
                System.out.println("Correctos: " + respuesta[0] + " - Presentes: " + respuesta[1]);

                if (respuesta[0] == 4) {
                    acertado = true;
                    System.out.println("¡Has adivinado la secuencia!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
