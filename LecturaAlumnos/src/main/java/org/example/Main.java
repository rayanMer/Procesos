package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/adat2";
        try (Connection con = DriverManager.getConnection(url, "dam2", "asdf.1234");) {
            String consultaLeer = "select * from alumnos";
            PreparedStatement read = con.prepareStatement(consultaLeer);
            ResultSet resultSet =read.executeQuery();
            System.out.println("---------------------------------------------------------");
            while(resultSet.next()){
                System.out.println("DNI: "+resultSet.getString(4));
                System.out.println("Nombre y apellidos: "+resultSet.getString(3)+","+resultSet.getString(2));
                System.out.println("Fecha de Nacimiento: "+resultSet.getString(5));
                System.out.println("NotaMedia: "+resultSet.getString(6));
                System.out.println("---------------------------------------------------------");
            }
            System.out.println("Lectura Completa");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }}