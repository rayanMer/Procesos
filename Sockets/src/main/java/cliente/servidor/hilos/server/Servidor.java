package cliente.servidor.hilos.server;

import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {

    public static void main(String[] args) {

        int numClientes = 0; // Contador de clientes 
        ServerSocket servidor;
        Socket cliente;
        int puerto = 6555;
        boolean salir = false;

        System.out.println("Servidor arrancado y escuchando...");
        try {
            servidor = new ServerSocket(puerto);
            while (!salir) {
                System.out.println("Esperando conexiones...");
                cliente = servidor.accept();//equivalente al reicve
                numClientes ++;
                // Pasamos el control al hilo correspondiente
                System.out.println("Peticion -> " + cliente.getInetAddress() + " --- " + cliente.getPort());
                GestionClientes gc = new GestionClientes(cliente, numClientes);
                gc.start();
            }
            System.out.println("Servidor finalizado...");
            servidor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
