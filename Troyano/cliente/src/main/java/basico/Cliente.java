package basico;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        int PUERTO = 5000; // Puerto del servidor


        // Solicitar consentimiento del usuario
        System.out.println("Este programa recopila la siguiente información de su sistema:");
        System.out.println("1. Nombre de usuario");
        System.out.println("2. Sistema operativo");
        System.out.println("3. Dirección IP");
        System.out.println("4. Nombre del equipo");
        System.out.println("5. Carpeta de trabajo");


            try {
                System.out.println("Conectándose al servidor...");
                InetAddress direccion = InetAddress.getLocalHost();
                Socket servidor = new Socket(direccion, PUERTO);
                System.out.println("Conexión establecida con el servidor.");

                // Recopilar información del sistema
                String sistemaOperativo = System.getProperty("os.name");
                String versionSO = System.getProperty("os.version");
                String arquitectura = System.getProperty("os.arch");
                String usuario = System.getProperty("user.name");
                String carpetaTrabajo = System.getProperty("user.dir");
                String ipCliente = direccion.getHostAddress();
                String nombreEquipo = direccion.getHostName();

                String datos = String.format(
                        "Usuario: %s\n" +
                                "Sistema Operativo: %s\n" +
                                "Versión SO: %s\n" +
                                "Arquitectura: %s\n" +
                                "Carpeta de Trabajo: %s\n" +
                                "IP del Cliente: %s\n" +
                                "Nombre del Equipo: %s\n",
                        usuario, sistemaOperativo, versionSO, arquitectura, carpetaTrabajo, ipCliente, nombreEquipo
                );

                // Enviar datos al servidor
                DataOutputStream salida = new DataOutputStream(servidor.getOutputStream());
                salida.writeUTF(datos);
                System.out.println("Datos enviados al servidor.");

                servidor.close(); // Cerrar conexión
                System.out.println("Conexión cerrada.");
            } catch (Exception e) {
                e.printStackTrace();
            }


    }
}
