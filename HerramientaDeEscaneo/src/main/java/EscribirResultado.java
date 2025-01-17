import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class EscribirResultado {
    public static void guardarResultados(Map<Integer, String> resultado, String nombreArchivo) {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());

        xstream.alias("ResultadoPuertos", Map.class);
        xstream.alias("Puerto", String.class);

        String out = xstream.toXML(resultado);

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(out);
        } catch (IOException e) {
            System.out.println("Error al guardar los resultados: " + e.getMessage());
        }
    }
}
