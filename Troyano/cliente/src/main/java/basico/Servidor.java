package basico;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        int PUERTO = 5000;
        System.out.println("Servidor escuchando en el puerto: " + PUERTO);

        try  {
            ServerSocket servidor = new ServerSocket(PUERTO);
            while (true) {
                System.out.println("Esperando cliente");
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado desde: " + cliente.getInetAddress());

                // recibir datos del cliente
                DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                String datosCliente = entrada.readUTF(); // leer info enviada por el cliente
                System.out.println("Datos recibidos: \n" + datosCliente);

                // mensaje que se enviara al cliente
                String mensaje = "¡Grande!! Gran guerrero, dices. mmmm." +
                        "\n La guerra no hacer grande a nadie.\n\n";
                mensaje += "Datos del sistema del cliente: \n" + datosCliente;

                // enviar el mensaje al cliente
                DataOutputStream ps = new DataOutputStream(cliente.getOutputStream());
                ps.writeUTF(mensaje);

                cliente.close();
                System.out.println("conexion cerrada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
