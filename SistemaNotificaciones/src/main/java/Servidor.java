import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private static final int PUERTO = 6969;
    private List<Categoria> categorias;
    private List<Socket> clientes;

    public Servidor() {
        categorias = new ArrayList<>();
        clientes = new ArrayList<>();
        categorias.add(new Categoria("Teologia"));
        categorias.add(new Categoria("eportes"));
        categorias.add(new Categoria("Entretenimiento"));
    }

    public void iniciar() {
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servokilo iniciado en el puerto " + PUERTO);

            while (true) {
                Socket socketCliente = servidor.accept();
                clientes.add(socketCliente);
                new GestionCliente(socketCliente, categorias).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void publicarNoticia(String categoria, String contenido) {
        Categoria categoriaObj = categorias.stream()
                .filter(c -> c.getNombre().equals(categoria))
                .findFirst().orElse(null);

        if (categoriaObj != null) {
            Noticia noticia = new Noticia(categoria, contenido);
            categoriaObj.notificar(noticia); // Notificar a los susriptoes
        }
    }


}
