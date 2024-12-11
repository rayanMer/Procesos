package distribuidor;

import escenario.Secadero;
import modelo.Jamon;
import tienda.Tienda;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Distribuidor extends Thread {
    private final Secadero secadero;
    private final List<Tienda> tiendas;
    private int loteId;

    public Distribuidor(Secadero secadero, List<Tienda> tiendas) {
        this.secadero = secadero;
        this.tiendas = tiendas;
        this.loteId = 1;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (true) {
            try {
                List<Jamon> lote = new ArrayList<>();
                // extraer 3 jamones por lote
                extraer3Jamones(lote);
                String nombreManifiesto = generarManifiesto(lote);

                // selecciona una tienda aleatoriamente para enviar el lote
                Tienda tiendaSeleccionada = tiendas.get(random.nextInt(tiendas.size()));
                tiendaSeleccionada.recibirLote(lote, nombreManifiesto);
                System.out.println("Distribuidor// Lote " + loteId + " enviado a " + tiendaSeleccionada.getNombre());

                loteId++;
                Thread.sleep(3000); // tiempo de distribucion
            } catch (InterruptedException e) {
                System.out.println("Distribuidor interrumpido.");
                break;
            }
        }
    }

    private void extraer3Jamones(List<Jamon> lote) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Jamon jamon = secadero.sacarJamon();
            lote.add(jamon);
        }
    }

    private String generarManifiesto(List<Jamon> lote) {
        String nombreManifiesto = "manifiesto_" + loteId + ".txt";
        generarManifiesto(nombreManifiesto, lote);
        System.out.println("Distribuidor: Lote " + loteId + " generado y registrado.");
        return nombreManifiesto;
    }

    private void generarManifiesto(String nombreArchivo, List<Jamon> lote) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("Manifiesto del lote " + loteId + ":\n");
            for (Jamon jamon : lote) {
                writer.write(jamon + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al generar el manifiesto: " + e.getMessage());
        }
    }
}
