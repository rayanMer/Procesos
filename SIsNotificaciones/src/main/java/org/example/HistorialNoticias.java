package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class HistorialNoticias {
    private static final String ARCHIVO_HISTORIAL = "historial.json";

    // guardar una noticia en el historial JSON
    public static void guardarNoticia(Noticia noticia) {
        JSONArray historial = cargarHistorial();
        JSONObject noticiaJSON = new JSONObject();
        noticiaJSON.put("categoria", noticia.getCategoria());
        noticiaJSON.put("contenido", noticia.getContenido());
        historial.put(noticiaJSON);

        // guardar el archivo actualizado
        try (FileWriter file = new FileWriter("historial.json")) {
            file.write(historial.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static JSONArray cargarHistorial() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_HISTORIAL));
            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea);
            }
            reader.close();
            return new JSONArray(contenido.toString());
        } catch (IOException e) {
            return new JSONArray(); //devuelve un historial vacío si  no existe
        }
    }



    // filstrar el historial por categoría
    public static List<Noticia> obtenerHistorialPorCategoria(String categoria) {
        JSONArray historial = cargarHistorial();
        List<Noticia> noticiasFiltradas = new ArrayList<>();

        for (int i = 0; i < historial.length(); i++) {
            JSONObject obj = historial.getJSONObject(i);
            if (obj.getString("categoria").equalsIgnoreCase(categoria)) {
                noticiasFiltradas.add(new Noticia(
                        obj.getString("categoria"),
                        obj.getString("contenido")
                ));
            }
        }
        return noticiasFiltradas;
    }
}
