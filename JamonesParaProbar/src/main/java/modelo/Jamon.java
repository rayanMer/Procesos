package modelo;

import java.util.UUID;

public class Jamon {
    private final String id;
    private final String granjaOrigen;
    private final double peso;

    public Jamon(String granjaOrigen, double peso) {
        this.id = UUID.randomUUID().toString();
        this.granjaOrigen = granjaOrigen;
        this.peso = peso;
    }
    public String getId() {
        return id;
    }
    public String getGranjaOrigen() {
        return granjaOrigen;
    }
    public double getPeso() {
        return peso;
    }
    @Override
    public String toString() {
        return "Jamón{" +
                "ID='" + id + '\'' +
                ", Origen='" + granjaOrigen + '\'' +
                ", Peso=" + peso +
                '}';
    }
}
