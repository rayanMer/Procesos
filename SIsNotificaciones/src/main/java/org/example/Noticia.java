package org.example;

import org.json.JSONObject;

public class Noticia {
    private String categoria;
    private String contenido;

    public Noticia(String categoria, String contenido) {
        this.categoria = categoria;
        this.contenido = contenido;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getContenido() {
        return contenido;
    }


}
