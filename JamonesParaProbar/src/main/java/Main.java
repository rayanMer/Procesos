import distribuidor.Distribuidor;
import escenario.Secadero;
import productor.Granja;
import tienda.Tienda;
import cliente.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la capacidad máxima del secadero: ");
        int capacidadSecadero = scanner.nextInt();
        Secadero secadero = new Secadero(capacidadSecadero);

        List<Tienda> tiendas = new ArrayList<>();
        System.out.print("Ingrese la cantidad de tiendas: ");
        int cantidadTiendas = scanner.nextInt();
        for (int i = 0; i < cantidadTiendas; i++) {
            System.out.print("Capacidad de lotes para Tienda " + i + ": ");
            int capacidadLotes = scanner.nextInt();
            tiendas.add(new Tienda("Tienda " + i, capacidadLotes));
        }

        System.out.print("Ingrese la cantidad de jamones que produce cada granja: ");
        int totalJamones = scanner.nextInt();

        Granja granja1 = new Granja("Granja 1", 3000, secadero, totalJamones);
        Granja granja2 = new Granja("Granja 2", 2000, secadero, totalJamones);
        Granja granja3 = new Granja("Granja 3", 2500, secadero, totalJamones);

        Distribuidor distribuidor = new Distribuidor(secadero, tiendas);

        System.out.print("Ingrese la cantidad de clientes: ");
        int cantidadClientes = scanner.nextInt();
        List<Cliente> clientes = new ArrayList<>();
        for (int i = 1; i <= cantidadClientes; i++) {
            clientes.add(new Cliente("Cliente " + i, tiendas));
        }

        // inicio hilos
        granja1.start();
        granja2.start();
        granja3.start();
        distribuidor.start();
        for (Cliente cliente : clientes) {
            cliente.start();
        }

        // espero a que terminen las granjas
        granja1.join();
        granja2.join();
        granja3.join();

        // detengo distribuidor y clientes
        distribuidor.interrupt();
        for (Cliente cliente : clientes) {
            cliente.interrupt();
        }

        System.out.println("Producción y venta de jamones finalizada.");
        scanner.close();
    }
}
