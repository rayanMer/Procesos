import distribuidor.Distribuidor;
import escenario.Secadero;
import productor.Granja;
import tienda.Tienda;
import cliente.Cliente;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Configuración inicial
        Secadero secadero = new Secadero(20);

        // Crear tiendas
        List<Tienda> tiendas = new ArrayList<>();
        tiendas.add(new Tienda("Tienda 1", 2));
        tiendas.add(new Tienda("Tienda 2", 2));
        tiendas.add(new Tienda("Tienda 3", 2));

        // Crear granjas (con diferentes tiempos de producción)
        Granja granja1 = new Granja("Granja 1", 3000, secadero, 50);
        Granja granja2 = new Granja("Granja 2", 2000, secadero, 50);
        Granja granja3 = new Granja("Granja 3", 2500, secadero, 50);

        // Crear distribuidor
        Distribuidor distribuidor = new Distribuidor(secadero, tiendas);

        // Crear clientes
        List<Cliente> clientes = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            clientes.add(new Cliente("Cliente " + i, tiendas));
        }

        // Iniciar hilos
        granja1.start();
        granja2.start();
        granja3.start();
        distribuidor.start();
        for (Cliente cliente : clientes) {
            cliente.start();
        }

        // Esperar a que terminen las granjas
        granja1.join();
        granja2.join();
        granja3.join();

        // Detener el distribuidor y clientes
        distribuidor.interrupt();
        for (Cliente cliente : clientes) {
            cliente.interrupt();
        }

        System.out.println("Producción y venta de jamones finalizada.");
    }
}
