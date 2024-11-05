package org.example;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class InsertarAlumnos {
    static Scanner entrada = new Scanner(System.in);
    public static void main(String[]args){
        if (args.length != 1) {
            System.out.println("Introduzca solo una palabra");
            System.exit(0);
        }
        String datos = args[0];
        System.out.println("a**********************************");
        System.out.println(datos);
        System.out.println("****************************************");
        String[] campos = datos.split(",");
        String nombre = campos[0].trim();
        String apellidos = campos[1];
        String DNI = campos[2].trim();
        String fechaNac = campos[3].trim();
        String notaMedia = campos[4].trim();
        String url = "jdbc:mysql://localhost:3306/adat2";
        try (Connection con = DriverManager.getConnection(url, "dam2", "asdf.1234");) {
            String consultaInsert = "INSERT INTO alumnos (nombre, apellido, dni,fecha_nac,nota_media) VALUES (?,?,?,?,?)";

            PreparedStatement insert = con.prepareStatement(consultaInsert);

            insert.setString(1, nombre);
            insert.setString(2, apellidos);
            insert.setString(3, DNI);
            // Convertir fechaNac de String a java.sql.Date en formato yyyy-MM-dd
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            try {
                java.util.Date fechaUtil = formato.parse(fechaNac);
                Date fechaSQL = new Date(fechaUtil.getTime());
                insert.setDate(4, fechaSQL);
            } catch (ParseException e) {
                System.err.println("Error al convertir la fecha: " + e.getMessage());
            }
            insert.setString(5, notaMedia);
            insert.executeUpdate();
            System.out.println("Insert realizado correctamente.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}






