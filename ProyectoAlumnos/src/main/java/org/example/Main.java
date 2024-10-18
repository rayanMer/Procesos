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
                switch (opc) {
                    case 1: {
                        System.out.println("Introduzca dni del alumno que desea buscar:");
                        break;
                    }
                    case 2: {
                        ArrayList<Alumno> array = new ArrayList<Alumno>();
                        ArrayList<Alumno> arrayRecuperado = new ArrayList<Alumno>();

                        System.out.println("Introduzca los datos del alumno que desea insertar");
                        boolean salir = false;
                        String nombre = null;
                        String apellidos = null;
                        String DNI = null;
                        String fechaNac = null;
                        String notaMedia = null;
                        while (!salir) {
                            System.out.println("Introduzca nombre:");
                            nombre = entrada.nextLine();
                            while (nombre.equals("") || nombre.length() == 0) {
                                System.err.println("Introduzca nombre que no este en blanco:");
                                nombre = entrada.nextLine();
                                if (nombre.equalsIgnoreCase("REINICIAR")) {
                                    salir = true;
                                }
                            }
                            if (nombre.equalsIgnoreCase("REINICIAR")) {
                                salir = true;
                            } else {
                                System.out.println("Introduzca apellidos:");
                                apellidos = entrada.nextLine();
                                while (apellidos.equals("") || apellidos.length() == 0) {
                                    System.err.println("Introduzca apellido que no este en blanco:");
                                    apellidos = entrada.nextLine();
                                    if (apellidos.equalsIgnoreCase("REINICIAR")) {
                                        salir = true;
                                    }
                                }
                                if (apellidos.equalsIgnoreCase("REINICIAR")) {
                                    salir = true;
                                } else {
                                    System.out.println("Introduzca DNI:");
                                    DNI = entrada.nextLine();
                                    while (DNI.equals("") || DNI.length() == 0) {
                                        System.err.println("Introduzca DNI que no este en blanco:");
                                        DNI = entrada.nextLine();
                                        if (DNI.equalsIgnoreCase("REINICIAR")) {
                                            salir = true;
                                        }
                                    }
                                    if (DNI.equalsIgnoreCase("REINICIAR")) {
                                        salir = true;
                                    } else {
                                        System.out.println("Introduzca fecha de nacimiento:");
                                        fechaNac = entrada.nextLine();
                                        String formatoFecha = "^\\d{2}/\\d{2}/\\d{4}$";
                                        while (fechaNac.equals("") || fechaNac.length() == 0 || !fechaNac.matches(formatoFecha)) {
                                            System.err.println("Introduzca fecha de nacimiento que no este en blanco:");
                                            fechaNac = entrada.nextLine();
                                            if (fechaNac.equalsIgnoreCase("REINICIAR")) {
                                                salir = true;
                                            }
                                            if (!fechaNac.matches(formatoFecha)) {
                                                System.err.println("Introduzca un formato valido dd/mm/yyyy");
                                            }
                                        }
                                        if (fechaNac.equalsIgnoreCase("REINICIAR")) {
                                            salir = true;
                                        } else {
                                            System.out.println("Introduzca nota media:");
                                            notaMedia = entrada.nextLine();
                                            if (notaMedia.equals("")) {
                                                notaMedia = "0";
                                            }
                                            salir = true;
                                        }
                                    }
                                }
                            }
                        }
                        Alumno alumno = new Alumno(nombre, apellidos, DNI, fechaNac, notaMedia);
                        System.out.println(alumno);
                        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("alumnos.dat"))) {
                            arrayRecuperado = (ArrayList<Alumno>) in.readObject();
                        } catch (IOException | ClassNotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                        arrayRecuperado.add(alumno);
                        try (
                                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("alumnos.dat"))) {
                                out.writeObject(arrayRecuperado);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }

                        break;

                    }
                    case 3: {
                        System.out.println("Obteniendo listado del alumnado");
                        ArrayList<Alumno> arrayRecuperado = null;

                        // Recuperar ArrayList desde el archivo
                        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("alumnos.dat"))) {
                            arrayRecuperado = (ArrayList<Alumno>) in.readObject();
                        } catch (IOException | ClassNotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                        if (arrayRecuperado != null) {
                            for (Alumno alumno : arrayRecuperado) {
                                System.out.println(alumno);
                            }
                        }
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
}