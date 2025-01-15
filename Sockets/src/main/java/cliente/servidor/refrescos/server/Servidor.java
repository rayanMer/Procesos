package cliente.servidor.refrescos.server;


import cliente.servidor.refrescos.model.Maquina;
import cliente.servidor.refrescos.model.Refresco;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        //numero de refrescos a meter
        int cantidad = 100;
        //lista de caramelos
        Maquina listaRefrescos = new Maquina();
        //creo los refrescos y los añado a la lista
        for (int i = 0; i < cantidad; i++) {
            listaRefrescos.add(new Refresco());
        }

        ServerSocket servidor;
        Socket cliente;
        int puerto = 5555;

        System.out.println("Servidor arrancado y esperando conexiones...");
        try {
            servidor = new ServerSocket(puerto);
            while (listaRefrescos.size() > 0) {//cuando se nos acaben los refrescos paramos el servidor
                System.out.println("Esperando...");
                cliente = servidor.accept();
                System.out.println("La maquina tiene: "+listaRefrescos.size()+" refrescos");
                System.out.println("Peticion de cliente -> " + cliente.getInetAddress() + " --- " + cliente.getPort());
                System.out.println("La maquina tiene: "+listaRefrescos.size()+" refrescos");
                GestionClientes gc = new GestionClientes(cliente, listaRefrescos);
                gc.start();
            }
            System.out.println("Servidor finalizado...");
            servidor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
