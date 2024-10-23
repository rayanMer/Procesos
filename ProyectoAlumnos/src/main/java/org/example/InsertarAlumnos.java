package org.example;

import java.util.Scanner;

public class InsertarAlumnos {
    static Scanner entrada = new Scanner(System.in);
    public static void main(String[]args){
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

    }
}
