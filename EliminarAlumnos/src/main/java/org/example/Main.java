package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        if (args.length!=1){
            System.exit(2);
        }
        String DNI=args[0];
        String url = "jdbc:mysql://localhost:3306/adat2";
        try (Connection con = DriverManager.getConnection(url, "dam2", "asdf.1234");) {
            String consulta = "delete from alumnos where DNI =?";
            PreparedStatement eliminar = con.prepareStatement(consulta);
            eliminar.setString(1,DNI);
            eliminar.executeUpdate();
            System.out.println("Alumno eliminado correctamente.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    }