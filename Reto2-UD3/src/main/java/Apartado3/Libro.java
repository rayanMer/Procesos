package Apartado3;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "libro") 															
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro") 												
    private int idLibro;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "genero")
    private String genero;

    @Column(name = "anio_publicacion")
    private int anioPublicacion;

    @ManyToOne
    @JoinColumn(name = "id_autor", referencedColumnName = "id_autor") 										
    private Autor autor;

    public Libro() {}

    public Libro(int idLibro, String titulo, String genero, int anioPublicacion, Autor autor) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.genero = genero;
        this.anioPublicacion = anioPublicacion;
        this.autor = autor;
    }

    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return "Libro{" +
                "idLibro=" + idLibro +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                ", autor=" + autor +
                '}';
    }
}
