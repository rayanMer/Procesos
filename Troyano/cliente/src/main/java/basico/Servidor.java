package basico;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        int PUERTO = 5000; // Puerto para escuchar
        System.out.println("Servidor escuchando en el puerto: " + PUERTO);

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            while (true) {
                System.out.println("Esperando cliente...");
                Socket cliente = servidor.accept(); // Aceptar conexión del cliente
                System.out.println("Cliente conectado desde: " + cliente.getInetAddress());

                // Recibir datos del cliente
                DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                String datosCliente = entrada.readUTF(); // Leer información enviada por el cliente
                System.out.println("Datos recibidos: \n" + datosCliente);

                // Guardar los datos en un archivo (opcional)
                try (FileWriter fw = new FileWriter("registro_clientes.txt", true)) {
                    fw.write("Datos de cliente desde " + cliente.getInetAddress() + ":\n");
                    fw.write(datosCliente + "\n\n");
                }

                // Enviar confirmación al cliente
                cliente.getOutputStream().write("Datos recibidos correctamente.\n".getBytes());

                cliente.close(); // Cerrar conexión con el cliente
                System.out.println("Conexión cerrada.\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
