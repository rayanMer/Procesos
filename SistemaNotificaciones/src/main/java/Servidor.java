import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Servidor {
    private static final int PUERTO = 6969;
    private List<Categoria> categorias;
    private List<GestionCliente> clientes;  // Lista para almacenar clientes conectados

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
            // Hilo para gestionar los comandos del menú
            new Thread(this::mostrarMenu).start();

            while (true) {
                Socket socketCliente = serverSocket.accept();
                GestionCliente cliente = new GestionCliente(socketCliente, categorias);
                clientes.add(cliente);  // Agregar cliente a la lista
                cliente.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mostrar el menú interactivo
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

    // Mostrar los clientes conectados
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

    // Mostrar las categorías disponibles
    public void mostrarCategorias() {
        System.out.println("\n--- Categorías disponibles ---");
        for (Categoria categoria : categorias) {
            System.out.println("  " + categoria.getNombre());
        }
    }

    // Agregar una nueva categoría
    public void agregarCategoria(Scanner scanner) {
        System.out.print("Introduce el nombre de la nueva categoría: ");
        String nuevaCategoria = scanner.nextLine();

        // Verificar si la categoría ya existe
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equalsIgnoreCase(nuevaCategoria)) {
                System.out.println("¡La categoría ya existe!");
                return;
            }
        }

        // Agregar la nueva categoría
        Categoria categoria = new Categoria(nuevaCategoria);
        categorias.add(categoria);
        System.out.println("Categoría '" + nuevaCategoria + "' añadida correctamente.");
    }

    // Publicar una noticia manualmente
    public void publicarNoticiaManual(Scanner scanner) {
        System.out.print("Introduce la categoría de la noticia: ");
        String categoria = scanner.nextLine();
        System.out.print("Introduce el contenido de la noticia: ");
        String contenido = scanner.nextLine();
        publicarNoticia(categoria, contenido);
    }

    public void publicarNoticia(String categoria, String contenido) {
        boolean categoriaEncontrada = false;
        for (Categoria c : categorias) {
            System.out.println("Comparando: " + c.getNombre() + " con " + categoria);
            if (c.getNombre().equalsIgnoreCase(categoria)) {
                categoriaEncontrada = true;
                c.notificar(new Noticia(categoria, contenido));
                return;
            }
        }
        if (!categoriaEncontrada) {
            System.out.println("Categoría no encontrada.");
        }
    }


    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        new Thread(servidor::iniciar).start();
    }
}
