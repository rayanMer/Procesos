package Apartado3;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class leerBaseDatos {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("libro");
    static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        Autor autor = new Autor( "Julio", "Cortázar", "Argentina", "1914-08-26");
        Autor autor1 = new Autor( "Luis", "aasd", "Argentina", "1914-08-26");
        Autor autor2 = new Autor( "Rayan", "asd", "Argentasddina", "1914-08-26");

        Libro libro = new Libro( "Rayuela", "Ficción", 1963, autor);
        Libro libro1 = new Libro( "libro1", "accion", 2005, autor1);
        Libro libro2 = new Libro( "libro2", "drama", 2006, autor2);

        //Añadir

        añadir(autor, libro);
        añadir(autor1, libro1);
        añadir(autor2, libro2);


        //Modificando la tabla el nombre del autor
       // modificar(autor);
        //Listar
        obtenerLibrosDeAutor(autor);
        
    }
    public static void añadir(Autor autor, Libro libro) {
    	try {
            // Crear tablas y persistir datos
            em.getTransaction().begin();
            em.persist(autor);
            em.persist(libro);
            em.getTransaction().commit();
            System.out.println("Datos persistidos correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void modificar(Autor autor) {
    	try {
            // Crear tablas y persistir datos
    		em.getTransaction().begin();
    		autor.setNombre("Julio Florencio"); // Modificar el nombre
    		em.merge(autor); // Actualizar el autor en la base de datos
    		em.getTransaction().commit();
    		System.out.println("Datos actualizados correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void obtenerLibrosDeAutor(Autor autor) {
    	try {
    		em.getTransaction().begin();
    		List<Libro> libros = em.createQuery("select l FROM Libro l WHERE l.autor.idAutor = :idAutor", Libro.class)
    		                       .setParameter("idAutor", autor.getIdAutor())
    		                       .getResultList();
    		em.getTransaction().commit();
    		libros.forEach(libro -> System.out.println(libro.getTitulo()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
