import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Observer;

public class GestionCliente extends Thread implements Observer {
    private Socket socket;
    private List<Categoria> categorias;
    private DataInputStream entradaDatos;
    private DataOutputStream salidaDatos;

    public GestionCliente(Socket socket, List<Categoria> categorias) {
        this.socket = socket;
        this.categorias = categorias;
        try {
            entradaDatos = new DataInputStream(socket.getInputStream());
            salidaDatos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String mensaje = entradaDatos.readUTF();
                // Procesar mensaje de suscripción o desconexión
                if (mensaje.startsWith("SUBSCRIBE")) {
                    String categoria = mensaje.split(" ")[1];
                    Categoria categoriaObj = categorias.stream()
                            .filter(c -> c.getNombre().equals(categoria))
                            .findFirst().orElse(null);
                    if (categoriaObj != null) {
                        categoriaObj.agregarSuscriptor(this); // Agregar el cliente como suscriptor
                        salidaDatos.writeUTF("Te has suscrito a la categoría: " + categoria);
                    }
                } else if (mensaje.startsWith("UNSUBSCRIBE")) {
                    String categoria = mensaje.split(" ")[1];
                    Categoria categoriaObj = categorias.stream()
                            .filter(c -> c.getNombre().equals(categoria))
                            .findFirst().orElse(null);
                    if (categoriaObj != null) {
                        categoriaObj.eliminarSuscriptor(this); // Eliminar el cliente de la suscripción
                        salidaDatos.writeUTF("Te has desuscrito de la categoría: " + categoria);
                    }
                } else if (mensaje.equals("EXIT")) {
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        if (arg instanceof Noticia) {
            Noticia noticia = (Noticia) arg;
            // Actualizar la interfaz de usuario en el hilo principal
            SwingUtilities.invokeLater(() -> {
                try {
                    salidaDatos.writeUTF(noticia.toString()); // Enviar la noticia al cliente
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


}
