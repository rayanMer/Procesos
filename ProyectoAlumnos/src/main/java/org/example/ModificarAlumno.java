package org.example;

import java.sql.*;
import java.util.Scanner;

public class ModificarAlumno {
    static  Scanner entrada = new Scanner(System.in);
    public static void main(String[] args) {
        if (args.length!=1){
            System.exit(2);
        }
        String DNI=args[0];

        String[] campos = pedirDatosAlumnoInsert().split(",");
        String nombre = campos[0].trim();
        String apellidos = campos[1];
        String fechaNac = campos[3].trim();
        String notaMedia = campos[4].trim();

        String url = "jdbc:mysql://localhost:3306/adat2";
        try (Connection con = DriverManager.getConnection(url, "dam2", "asdf.1234");) {
            String consultaPorDNI = "update alumnos SET nombre = ?, apellidos = ?, fechaNac = ?, notaMedia = ? where DNI = ?";
            PreparedStatement update = con.prepareStatement(consultaPorDNI);
            update.setString(1, nombre);
            update.setString(2, apellidos);
            update.setString(3, fechaNac);
            update.setString(4, notaMedia);
            update.setString(5, DNI);
            update.executeUpdate();
            System.out.println("Modificacion realizada correctamente.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String pedirDatosAlumnoInsert() {
        boolean salir = false;
        String nombre;
        String apellidos;
        String fechaNac;
        String notaMedia;
        while (!salir) {
            System.out.println("Introduzca nombre modificado:");
            nombre = entrada.nextLine();
            while (nombre.isEmpty()) {
                System.err.println("Introduzca nombre que no este en blanco:");
                nombre = entrada.nextLine();
            }

            System.out.println("Introduzca apellidos:");
            apellidos = entrada.nextLine();
            while (apellidos.isEmpty()) {
                System.err.println("Introduzca apellido que no este en blanco:");
                apellidos = entrada.nextLine();
            }

            System.out.println("Introduzca fecha de nacimiento:");
            fechaNac = entrada.nextLine();
            String formatoFecha = "^\\d{2}/\\d{2}/\\d{4}$";
            while (fechaNac.isEmpty() || !fechaNac.matches(formatoFecha)) {
                System.err.println("Introduzca fecha de nacimiento que no este en blanco:");
                fechaNac = entrada.nextLine();
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
        return null;
    }
}
