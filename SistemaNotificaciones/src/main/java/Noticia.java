public class Noticia {
    private String categoria;
    private String contenido;

    public Noticia(String categoria, String contenido) {
        this.categoria = categoria;
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Notia en " + categoria + ": " + contenido;
    }
}
