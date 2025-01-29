import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import org.json.JSONObject;
import java.util.*;

class GestionCliente extends Thread implements Observer {
    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private List<Categoria> categorias;  // lista del servidor para copiar
    private List<Categoria> suscripciones;

    public GestionCliente(Socket socket, List<Categoria> categorias) {
        this.socket = socket;
        this.categorias = categorias;  // recibir lista del servidor con las categorias
        this.suscripciones = new ArrayList<>();
        try {
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        // Este método se llama cuando una categoría publica una noticia
        if (o instanceof Categoria) {
            Noticia noticia = (Noticia) arg;
            try {
                salida.writeUTF("Nueva noticia en " + noticia.getCategoria() + ": " + noticia.getContenido());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String mensaje = entrada.readUTF();
                JSONObject json = new JSONObject(mensaje);
                String comando = json.getString("comando");
                String categoria = json.optString("categoria", "");

                switch (comando) {
                    case "SUBSCRIBE":
                        suscribirse(categoria);
                        break;
                    case "UNSUBSCRIBE":
                        desuscribirse(categoria);
                        break;
                    case "EXIT":
                        socket.close();
                        return;
                }
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado.");
        }
    }

    private void suscribirse(String categoria) throws IOException {
        boolean categoriaEncontrada = false;
        // buscar categoria en la lista
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(categoria)) {
                //si no esta suscrito lo mete
                if (!suscripciones.contains(c)) {
                    suscripciones.add(c);
                    c.addObserver(this);  // El cliente ya es un observer
                    salida.writeUTF("Suscrito a: " + categoria);
                } else {
                    salida.writeUTF("Ya estás suscrito a esta categoría.");
                }
                categoriaEncontrada = true;
                break;
            }
        }

        if (!categoriaEncontrada) {
            salida.writeUTF("Categoría no encontrada");
        }
    }

    private void desuscribirse(String categoria) throws IOException {
        for (Categoria c : suscripciones) {
            if (c.getNombre().equalsIgnoreCase(categoria)) {
                c.deleteObserver(this);  // el cliente ya o es observer
                suscripciones.remove(c);
                salida.writeUTF("Desuscrito de: " + categoria);
                return;
            }
        }
        salida.writeUTF("No estás suscrito a esa categoría");
    }

    // Getter para obtener el socket
    public Socket getSocket() {
        return socket;
    }

    // Getter para obtener las suscripciones
    public List<Categoria> getSuscripciones() {
        return suscripciones;
    }
}
