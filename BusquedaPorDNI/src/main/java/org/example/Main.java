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
            String consultaPorDNI = "select * from alumnos where DNI =?";
            PreparedStatement read = con.prepareStatement(consultaPorDNI);
            read.setString(1,DNI);
            ResultSet resultSet =read.executeQuery();
            System.out.println("---------------------------------------------------------");
            while(resultSet.next()){
                System.out.println("DNI: "+resultSet.getString(4));
                System.out.println("Nombre y apellidos: "+resultSet.getString(3)+","+resultSet.getString(2));
                System.out.println("Fecha de Nacimiento: "+resultSet.getString(5));
                System.out.println("NotaMedia: "+resultSet.getString(6));
                System.out.println("---------------------------------------------------------");
            }
            read.executeQuery();
            System.out.println("Busqueda realizada correctamente.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }}