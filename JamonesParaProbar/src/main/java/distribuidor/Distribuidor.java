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
                // Extraer 3 jamones por lote
                extraer3Jamones(lote);
                String nombreManifiesto = generarManifiestoXML(lote);

                // seleccina una tienda aleatoriamente para enviar el lote
                Tienda tiendaSeleccionada = tiendas.get(random.nextInt(tiendas.size()));
                tiendaSeleccionada.recibirLote(lote, nombreManifiesto);
                System.out.println("Distribuidor// Lote " + loteId + " enviado a " + tiendaSeleccionada.getNombre());

                loteId++;
                Thread.sleep(3000); // Tiempo de distribucion
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

    private String generarManifiestoXML(List<Jamon> lote) {
        String nombreManifiesto = "manifiesto_" + loteId + ".xml";
        generarXML(nombreManifiesto, lote);
        System.out.println("Distribuidor: Lote " + loteId + " generado y registrado en XML.");
        return nombreManifiesto;
    }

    private void generarXML(String nombreArchivo, List<Jamon> lote) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<manifiesto>\n");
            writer.write("    <lote id=\"" + loteId + "\">\n");
            for (Jamon jamon : lote) {
                writer.write("        <jamon>\n");
                writer.write("            <id>" + jamon.getId() + "</id>\n");
                writer.write("            <peso>" + jamon.getPeso() + "</peso>\n");
                writer.write("            <granjaOrigen>" + jamon.getGranjaOrigen() + "</granjaOrigen>\n");
                writer.write("        </jamon>\n");
            }
            writer.write("    </lote>\n");
            writer.write("</manifiesto>\n");
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
