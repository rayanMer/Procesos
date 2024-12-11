import distribuidor.Distribuidor;
import escenario.Secadero;
import productor.Granja;
import tienda.Tienda;
import cliente.Cliente;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Secadero secadero = new Secadero(20);

        List<Tienda> tiendas = new ArrayList<>();
        //crear 3 tiendas
        for(int i=0;i<3;i++) {
            tiendas.add(new Tienda("Tienda " + i, 2));
        }

        Granja granja1 = new Granja("Granja 1", 3000, secadero, 50);
        Granja granja2 = new Granja("Granja 2", 2000, secadero, 50);
        Granja granja3 = new Granja("Granja 3", 2500, secadero, 50);
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
        //esperar a que terminen granjsa
        granja1.join();
        granja2.join();
        granja3.join();
        // detener distribuidor y cliente
        distribuidor.interrupt();
        for (Cliente cliente : clientes) {
            cliente.interrupt();
        }

        System.out.println("Producción y venta de jamones finalizada.");
    }
}
