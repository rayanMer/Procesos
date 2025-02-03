import java.util.ArrayList;
import java.util.List;

class Categoria {
    private String nombre;
    private List<Noticia> historialNoticias;  // lista para almacenar el historial de noticias

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
        historialNoticias.add(noticia);  // añadir noticia al historia
    }
}
