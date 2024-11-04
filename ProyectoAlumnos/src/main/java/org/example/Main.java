package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {

        int opc = 0;
        while (opc != 4) {
            System.out.println("1. Búsqueda de alumno por DNI.");
            System.out.println("2. Insertar un nuevo alumno.");
            System.out.println("3. Obtener todos los alumnos.");
            System.out.println("4. Finalizar.");
            System.out.println("Introduzca una opcion:");
            opc = entrada.nextInt();
            if (opc < 1 || opc > 4) {
                System.err.println("Introduzca una opcion valida");
            } else {

            }
                switch (opc) {
                    case 1: {
                        System.out.println("Introduzca dni del alumno que desea buscar:");
                        break;
                    }
                    case 2: {
                        System.out.println("Introduzca los datos del alumno que desea insertar");
                        String path = System.getProperty("user.home") + File.separator + "IdeaProjects" + File.separator + "insertarAlumnos" + File.separator + "out" + File.separator + "artifacts" + File.separator + "insertarAlumnos_jar" + File.separator;
                        String jarFile = "insertarAlumnos.jar";
                        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                        System.out.println(System.getProperty("java.home"));
                       ProcessBuilder pb = new ProcessBuilder(java + " -jar " + path + jarFile);
                       // ProcessBuilder pb = new ProcessBuilder("java", "-jar", path + jarFile);
                        try {
                            Process proceso = pb.start();
                        } catch (IOException  e) {
                            e.printStackTrace();
                        }
                        break;

                    }
                    case 3: {
                        System.out.println("Obteniendo listado del alumnado");
                        break;
                    }
                    case 4: {
                        System.out.println("Finalizando rograma");
                        break;
                    }
                }
            }
        }


    }
