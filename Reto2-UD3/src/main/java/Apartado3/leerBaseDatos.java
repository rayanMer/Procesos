package Apartado3;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class leerBaseDatos {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("libro");
    static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        Autor autor = new Autor("Julio", "Cortázar", "Argentina", "1914-08-26");
        Autor autor1 = new Autor("Luis", "aasd", "Argentina", "1914-08-26");
        Autor autor2 = new Autor("Rayan", "asd", "Argentasddina", "1914-08-26");

        Libro libro = new Libro("Rayuela", "Ficción", 1963, autor);
        Libro libro1 = new Libro("libro1", "accion", 2005, autor1);
        Libro libro2 = new Libro("libro2", "drama", 2006, autor2);


       // obtenerTodosLosAutores();
        obtenerAutorPorId(6);
        //obtenerLibrosDeAutorPorNombre("Rayan");
        //obtenerLibrosPorRangoDeAnios(1960,2000);
        //contarLibrosDeAutor("Rayan");
        // obtenerLibrosConAutor();
        // obtenerAutoresOrdenadosPor("nombre",true);
        //obtenerAutoresConMasDeUnLibro();
    }

    public static void añadir(Libro libro, Autor autor) {
        try {
            em.getTransaction().begin();
            em.persist(autor);
            em.persist(libro);
            em.getTransaction().commit();
            System.out.println("Autor y libro añadidos correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }


    public static void obtenerTodosLosAutores() {
        try {
            em.getTransaction().begin();
            List<Autor> autores = em.createQuery("SELECT a FROM Autor a", Autor.class).getResultList();
            em.getTransaction().commit();
            autores.forEach(autor -> System.out.println(autor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void obtenerAutorPorId(int id) {
        try {
            Autor autor = em.find(Autor.class, id);
            System.out.println(autor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void obtenerLibrosDeAutorPorNombre(String nombreAutor) {
        try {
            em.getTransaction().begin();
            List<Libro> libros = em.createQuery(
                            "SELECT l FROM Libro l WHERE l.autor.nombre = :nombre", Libro.class)
                    .setParameter("nombre", nombreAutor)
                    .getResultList();
            em.getTransaction().commit();
            libros.forEach(libro -> System.out.println(libro.getTitulo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void obtenerLibrosPorRangoDeAnios(int inicioRango, int finRango) {
        try {
            em.getTransaction().begin();
            List<Libro> libros = em.createQuery(
                            "SELECT l FROM Libro l WHERE l.anioPublicacion BETWEEN :inicio AND :fin", Libro.class)
                    .setParameter("inicio", inicioRango)
                    .setParameter("fin", finRango)
                    .getResultList();
            em.getTransaction().commit();
            libros.forEach(libro -> System.out.println(libro.getTitulo() + " (" + libro.getAnioPublicacion() + ")"));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public static void contarLibrosDeAutor(String nombreAutor) {
        try {
            em.getTransaction().begin();
            Long count = em.createQuery(
                            "SELECT COUNT(l) FROM Libro l WHERE l.autor.nombre = :nombre", Long.class)
                    .setParameter("nombre", nombreAutor)
                    .getSingleResult();
            em.getTransaction().commit();
            System.out.println("Número de libros de " + nombreAutor + ": " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void obtenerLibrosConAutor() {
        try {
            em.getTransaction().begin();
            List<Object[]> resultados = em.createQuery(
                            "SELECT a.nombre, l.titulo FROM Autor a JOIN a.libros l", Object[].class)
                    .getResultList();

            em.getTransaction().commit();
            for (Object[] resultado : resultados) {
                String nombreAutor = (String) resultado[0];
                String tituloLibro = (String) resultado[1];
                System.out.println("Autor: " + nombreAutor + " - Libro: " + tituloLibro);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public static void obtenerAutoresOrdenadosPor(String campoOrden, boolean ascendente) {
        try {
            em.getTransaction().begin();
            String orden = ascendente ? "ASC" : "DESC";
            String query = "SELECT a FROM Autor a ORDER BY a." + campoOrden + " " + orden;
            List<Autor> autores = em.createQuery(query, Autor.class).getResultList();
            em.getTransaction().commit();
            autores.forEach(autor -> System.out.println(autor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Subconsulta
    public static void obtenerAutoresConMasDeUnLibro() {
        try {
            em.getTransaction().begin();
            List<Autor> autores = em.createQuery(
                            "SELECT a FROM Autor a WHERE (SELECT COUNT(l) FROM Libro l WHERE l.autor = a) > 1", Autor.class)
                    .getResultList();
            em.getTransaction().commit();
            for (Autor autor : autores) {
                System.out.println("Autor: " + autor.getNombre() + " " + autor.getApellido());
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }


}