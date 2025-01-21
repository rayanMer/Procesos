package Apartado3;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class leerBaseDatos {
    public static void main(String[] args) {
        Autor autor = new Autor(3, "Julio", "Cortázar", "Argentina", "1914-08-26");
        Libro libro = new Libro(2, "Rayuela", "Ficción", 1963, autor);
        //Añadir
        añadir(autor, libro);
        //Modificando la tabla el nombre del autor
//     modificar(autor);
        //Listar
  //      obtenerLibrosDeAutor(autor);
        
    }
    public static void añadir(Autor autor, Libro libro) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("libro");
        EntityManager em = emf.createEntityManager();
    	try {
            // Crear tablas y persistir datos
            em.getTransaction().begin();
            em.persist(autor);
            em.persist(libro);
            em.getTransaction().commit();
            System.out.println("Datos persistidos correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public static void modificar(Autor autor) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("libro");
        EntityManager em = emf.createEntityManager();
    	try {
            // Crear tablas y persistir datos
    		em.getTransaction().begin();
    		autor.setNombre("Julio Florencio"); // Modificar el nombre
    		em.merge(autor); // Actualizar el autor en la base de datos
    		em.getTransaction().commit();
    		System.out.println("Datos actualizados correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    public static void obtenerLibrosDeAutor(Autor autor) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("libro");
        EntityManager em = emf.createEntityManager();
    	try {
    		em.getTransaction().begin();
    		List<Libro> libros = em.createQuery("select l FROM Libro l WHERE l.autor.idAutor = :idAutor", Libro.class)
    		                       .setParameter("idAutor", autor.getIdAutor())
    		                       .getResultList();
    		em.getTransaction().commit();
    		libros.forEach(libro -> System.out.println(libro.getTitulo()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
