import com.thoughtworks.xstream.XStream;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ResultWriter {
    public static void guardarResultados(Map<Integer, String> resultado, String nombreArchivo) {
        XStream xstream = new XStream();

        xstream.alias("ResultadoPuertos", Map.class);
        xstream.alias("Puerto", String.class);

        String output = xstream.toXML(resultado);

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(output);
        } catch (IOException e) {
            System.out.println("Error al guardar los resultados: " + e.getMessage());
        }
    }
}
