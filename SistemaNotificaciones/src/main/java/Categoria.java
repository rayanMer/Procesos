import java.util.ArrayList;
import java.util.List;

class Categoria {
    private String nombre;
    private List<Noticia> historialNoticias;  // Lista para almacenar el historial de noticias

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.historialNoticias = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Noticia> getHistorialNoticias() {
        return historialNoticias;
    }

    public void agregarNoticia(Noticia noticia) {
        historialNoticias.add(noticia);  // Añadir una noticia al historial
    }
}
