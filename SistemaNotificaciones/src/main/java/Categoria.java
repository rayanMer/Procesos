import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Categoria extends Observable {
    private String nombre;
    private List<Observer> suscriptores;

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.suscriptores = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarSuscriptor(Observer suscriptor) {
        suscriptores.add(suscriptor);
    }

    public void eliminarSuscriptor(Observer suscriptor) {
        suscriptores.remove(suscriptor);
    }

    public void notificar(Noticia noticia) {
        setChanged(); // Marca que ha habido un cambio
        for (Observer suscriptor : suscriptores) {
            suscriptor.update(this, noticia); // Notifica a los suscriptores
        }
    }

}
