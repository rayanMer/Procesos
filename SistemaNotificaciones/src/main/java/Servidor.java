import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

class Servidor extends Observable {
    private static final int PUERTO = 6969;
    private List<Categoria> categorias;
    private List<GestionCliente> clientes;  // lista para almacenar clientes conectados

    public Servidor() {
        categorias = new ArrayList<>();
        categorias.add(new Categoria("Tecnologia"));
        categorias.add(new Categoria("Deportes"));
        categorias.add(new Categoria("Entretenimiento"));
        clientes = new ArrayList<>();
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            // crear y arrancar el hilo para mostrar el menú
            Thread menuThread = new Thread(this::mostrarMenu);
            menuThread.start();

            // aceptar conexiones de clientes mientras el menú está activo
            while (true) {
                Socket socketCliente = serverSocket.accept();
                GestionCliente cliente = new GestionCliente(socketCliente, categorias, this);
                clientes.add(cliente);  // agregar cliente a la lista
                addObserver(cliente);  // El servidor se vuelve observable
                cliente.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //menu
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Ver clientes conectados");
            System.out.println("2. Ver categorías");
            System.out.println("3. Agregar categoría");
            System.out.println("4. Publicar noticia");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    mostrarClientesConectados();
                    break;
                case "2":
                    mostrarCategorias();
                    break;
                case "3":
                    agregarCategoria(scanner);
                    break;
                case "4":
                    publicarNoticiaManual(scanner);
                    break;
                case "5":
                    System.out.println("Servidor detenido.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intenta nuevamente.");
            }
        }
    }

    // mostrar los clientes conectados
    public void mostrarClientesConectados() {
        System.out.println("\n--- Clientes conectados ---");
        for (GestionCliente cliente : clientes) {
            System.out.println("Cliente: " + cliente.getSocket().getInetAddress());
            System.out.println("  Suscrito a: ");
            for (Categoria c : cliente.getSuscripciones()) {
                System.out.println("    " + c.getNombre());
            }
        }
    }

    // mostrar las categorías disponibles
    public void mostrarCategorias() {
        System.out.println("\n--- Categorías disponibles ---");
        for (Categoria categoria : categorias) {
            System.out.println("  " + categoria.getNombre());
        }
    }

    // agregar una nueva categoría
    public void agregarCategoria(Scanner scanner) {
        System.out.print("Introduce el nombre de la nueva categoría: ");
        String nuevaCategoria = scanner.nextLine();

        // verificar si la categoría ya existe
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equalsIgnoreCase(nuevaCategoria)) {
                System.out.println("Error:La categoría ya existe");
                return;
            }
        }

        // agregar la nueva categoría
        Categoria categoria = new Categoria(nuevaCategoria);
        categorias.add(categoria);
        System.out.println("Categoría '" + nuevaCategoria + "' añadida correctamente.");
    }

    // oublica una noticia manualmente
    public void publicarNoticiaManual(Scanner scanner) {
        System.out.print("Introduce la categoría de la noticia: ");
        String categoria = scanner.nextLine();
        System.out.print("Introduce el contenido de la noticia: ");
        String contenido = scanner.nextLine();
        publicarNoticia(categoria, contenido);
    }

    // publicar una noticia y notificar a los clientes
    public void publicarNoticia(String categoria, String contenido) {
        Noticia noticia = new Noticia(categoria, contenido);
        HistorialNoticias.guardarNoticia(noticia); // Guardar en historial

        // buscar la categoría y agregar la noticia a su historial
        for (Categoria cat : categorias) {
            if (cat.getNombre().equalsIgnoreCase(categoria)) {
                cat.agregarNoticia(noticia);  // Añadir al historial de la categoría
                break;
            }
        }

        setChanged();  // marca el estado del servidor como "cambiado"
        notifyObservers(noticia);  // notifica a todos los clientes que son los observadores
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciar();
    }
}
