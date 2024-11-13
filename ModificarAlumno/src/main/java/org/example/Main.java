package org.example;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length!=2){
            System.err.println("Error");
            System.exit(2);
        }
        String DNI=args[0];

        String[] campos = args[1].split(",");
        String nombre = campos[0].trim();
        String apellidos = campos[1];
        String fechaNac = campos[2].trim();
        String notaMedia = campos[3].trim();
        String url = "jdbc:mysql://localhost:3306/adat2";
        try (Connection con = DriverManager.getConnection(url, "dam2", "asdf.1234");) {
            String consultaPorDNI = "update alumnos SET nombre = ?, apellido = ?, fecha_nac = ?, nota_media = ? where DNI = ?";
            PreparedStatement update = con.prepareStatement(consultaPorDNI);
            update.setString(1, nombre);
            update.setString(2, apellidos);
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            try {
                java.util.Date fechaUtil = formato.parse(fechaNac);
                Date fechaSQL = new Date(fechaUtil.getTime());
                update.setDate(3, fechaSQL);
            } catch (ParseException e) {
                System.err.println("Error al convertir la fecha: " + e.getMessage());
            }
            update.setString(4, notaMedia);
            update.setString(5, DNI);
            update.executeUpdate();
            System.out.println("Modificacion realizada correctamente.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}