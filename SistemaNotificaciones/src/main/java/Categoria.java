import java.util.Observable;

class Categoria extends Observable {
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void notificar(Noticia noticia) {
        setChanged();
        notifyObservers(noticia);  // notifica a los observadorees
    }
}
