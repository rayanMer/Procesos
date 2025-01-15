package basico;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {
        int PUERTO = 5000;

        Thread clienteThread = new Thread(() -> {
            conectarServidor(PUERTO);
        });


        clienteThread.start();
        int contador=0;
        while(true){
            System.out.println(contador++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean conectarServidor(int puerto){
        try {
            InetAddress direccion = InetAddress.getLocalHost();
            //"172.29.10.3"
            Socket servidor = new Socket(direccion, puerto);

            // info del sistema del cliente
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

            // enviar datos al servidor
            DataOutputStream salida = new DataOutputStream(servidor.getOutputStream());
            salida.writeUTF(datos);

            // recibir el mensaje del servidor
            DataInputStream mensajeRecibido = new DataInputStream(servidor.getInputStream());
            String mensaje = mensajeRecibido.readUTF();
            mostrarMensajeEnJTextArea(mensaje);
            servidor.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error al conectar al servidor: " + e.getMessage());
        }
        return false;
    }

    public static void mostrarMensajeEnJTextArea(String mensaje) {
        // Crear el JFrame
        JFrame frame = new JFrame("Mensaje del Servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        JTextArea textArea = new JTextArea();
        textArea.setText(mensaje);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}
