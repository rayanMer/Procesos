package org.example;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {

        int opc = 0;
        while (opc != 5) {
            System.out.println("1. Búsqueda de alumno por DNI.");
            System.out.println("2. Insertar un nuevo alumno.");
            System.out.println("3. Obtener todos los alumnos.");
            System.out.println("4. Eliminar alumno por DNI");
            System.out.println("5. Modificar alumno");
            System.out.println("6. Finalizar.");
            System.out.println("Introduzca una opcion:");
            opc = entrada.nextInt();
            if (opc < 1 || opc > 5) {
                System.err.println("Introduzca una opcion valida");
            } else {


                switch (opc) {
                    case 1: {
                        System.out.println("Introduzca dni del alumno que desea buscar:");
                        String DNI = pedirDNI();
                        String path = System.getProperty("user.home") + File.separator + "jar_files" + File.separator;
                        String jarFile = "BusquedaPorDNI.jar";
                        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                        ProcessBuilder pb = new ProcessBuilder( java, "-jar", path + jarFile, DNI
                        );
                        if (DNI != null) {
                            try {
                                Process proceso = pb.start();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    System.out.println(line);
                                }
                                proceso.waitFor();
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Introduzca los datos del alumno que desea insertar");
                        String path = System.getProperty("user.home") + File.separator + "jar_files" + File.separator;
                        String jarFile = "InsertarAlumnos.jar";
                        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                        String alumno = pedirDatosAlumnoInsert();
                        if (alumno != null) {
                            ProcessBuilder pb = new ProcessBuilder(
                                    java,
                                    "-jar",
                                    path + jarFile,
                                    alumno
                            );
                            try {
                                Process proceso = pb.start();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    System.out.println(line);
                                }
                                proceso.waitFor();
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("***********Reiniciando***********");
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Obteniendo listado del alumnado");
                        String path = System.getProperty("user.home") + File.separator + "jar_files" + File.separator;
                        String jarFile = "LecturaAlumnos.jar";
                        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                        ProcessBuilder pb = new ProcessBuilder(
                                java,
                                "-jar",
                                path + jarFile
                        );
                        try {
                            Process proceso = pb.start();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }
                            proceso.waitFor();
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case 4: {
                        System.out.println("Eliminación de alumno por DNI");
                        String DNI = pedirDNI();
                        String path = System.getProperty("user.home") + File.separator + "jar_files" + File.separator;
                        String jarFile = "EliminarAlumnos.jar";
                        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                        ProcessBuilder pb = new ProcessBuilder(
                                java,
                                "-jar",
                                path + jarFile,
                                DNI
                        );
                        if (DNI != null) {
                            try {
                                Process proceso = pb.start();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    System.out.println(line);
                                }
                                proceso.waitFor();
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case 5: {
                        System.out.println("Modificación de alumno");
                        String DNI = pedirDNI();
                        String datosNuevos = pedirDatosModificar();
                        String path = System.getProperty("user.home") + File.separator + "jar_files" + File.separator;
                        String jarFile = "ModificarAlumno.jar";
                        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                        ProcessBuilder pb = new ProcessBuilder(
                                java,
                                "-jar",
                                path + jarFile,
                                DNI,
                                datosNuevos
                        );
                        if (DNI != null && datosNuevos != null) {
                            try {
                                Process proceso = pb.start();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    System.out.println(line);
                                }
                                proceso.waitFor();
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case 6: {
                        System.out.println("Finalizando programa");
                        break;
                    }
                }
            }
        }
    }

    public static String pedirDNI() {
        System.out.println("Introduzca un DNI");
        String DNI = entrada.next();
        while (DNI.isEmpty()) {
            System.err.println("Introduzca un dni");
            DNI = entrada.next();
        }
        return DNI;
    }

    public static String pedirDatosAlumnoInsert() {
        boolean salir = false;
        String nombre;
        String apellidos;
        String DNI;
        String fechaNac;
        String notaMedia;
        while (!salir) {
            System.out.println("Introduzca nombre:");
            nombre = entrada.next();
            while (nombre.isEmpty()) {
                System.err.println("Introduzca nombre que no este en blanco:");
                nombre = entrada.nextLine();

            }
            if (nombre.equalsIgnoreCase("REINICIAR")) {
                salir = true;
            } else {
                System.out.println("Introduzca apellidos:");
                apellidos = entrada.next();
                while (apellidos.isEmpty()) {
                    System.err.println("Introduzca apellido que no este en blanco:");
                    apellidos = entrada.next();
                }
                if (apellidos.equalsIgnoreCase("REINICIAR")) {
                    salir = true;
                } else {
                    DNI = pedirDNI();
                    if (DNI.equalsIgnoreCase("REINICIAR")) {
                        salir = true;
                    } else {
                        System.out.println("Introduzca fecha de nacimiento:");
                        fechaNac = entrada.next();
                        String formatoFecha = "^\\d{2}/\\d{2}/\\d{4}$";
                        while (fechaNac.isEmpty() || !fechaNac.matches(formatoFecha)) {
                            System.err.println("Introduzca fecha de nacimiento que no este en blanco:");
                            fechaNac = entrada.next();
                            if (!fechaNac.matches(formatoFecha)) {
                                System.err.println("Introduzca un formato valido dd/mm/yyyy");
                            }
                        }
                        if (fechaNac.equalsIgnoreCase("REINICIAR")) {
                            salir = true;
                        } else {
                            System.out.println("Introduzca nota media:");
                            notaMedia = entrada.next();
                            if (notaMedia.isEmpty()) {
                                notaMedia = "0";
                            }
                            String datos = nombre + "," + apellidos + "," + DNI + "," + fechaNac + "," + notaMedia;
                            System.out.println("hecho");
                            return datos;
                        }
                    }
                }
            }
        }

        return null;
    }

    public static String pedirDatosModificar() {

        String nombre;
        String apellidos;
        String fechaNac;
        String notaMedia;
        System.out.println("Introduzca nombre modificado:");
        nombre = entrada.next();
        while (nombre.isEmpty()) {
            System.err.println("Introduzca nombre que no este en blanco:");
            nombre = entrada.nextLine();
        }
        System.out.println("Introduzca apellidos:");
        apellidos = entrada.next();
        while (apellidos.isEmpty()) {
            System.err.println("Introduzca apellido que no este en blanco:");
            apellidos = entrada.next();
        }
        System.out.println("Introduzca fecha de nacimiento:");
        fechaNac = entrada.next();
        String formatoFecha = "^\\d{2}/\\d{2}/\\d{4}$";
        while (fechaNac.isEmpty() || !fechaNac.matches(formatoFecha)) {
            System.err.println("Introduzca fecha de nacimiento que no este en blanco:");
            fechaNac = entrada.next();
            if (!fechaNac.matches(formatoFecha)) {
                System.err.println("Introduzca un formato valido dd/mm/yyyy");
            }
        }
        System.out.println("Introduzca nota media:");
        notaMedia = entrada.nextLine();
        if (notaMedia.isEmpty()) {
            notaMedia = "0";
        }
        String datos = nombre + "," + apellidos + "," + fechaNac + "," + notaMedia;
        return datos;
    }
}
