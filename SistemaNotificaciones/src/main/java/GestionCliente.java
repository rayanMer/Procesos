import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONObject;

class GestionCliente extends Thread implements Observer {
    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private List<Categoria> categorias;  // lista de categorías del servidor
    private List<Categoria> suscripciones;
    private Servidor servidor;  // Referencia al servidor

    // Constructor modificado para recibir la referencia del servidor
    public GestionCliente(Socket socket, List<Categoria> categorias, Servidor servidor) {
        this.socket = socket;
        this.categorias = categorias;  // recibir lista de categorías del servidor
        this.suscripciones = new ArrayList<>();
        this.servidor = servidor;  // Almacenar la referencia al servidor
        try {
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        // Este método se llama cuando el servidor publica una noticia
        if (arg instanceof Noticia) {
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
                    case "1":
                        suscribirse(categoria);
                        break;
                    case "2":
                        desuscribirse(categoria);
                        break;
                    case "3":
                        socket.close();
                        return;
                    case "4":
                        List<Noticia> historial = HistorialNoticias.obtenerHistorialPorCategoria(categoria);
                        if (historial.isEmpty()) {
                            salida.writeUTF("No hay noticias en la categoría: " + categoria);
                        } else {
                            salida.writeUTF("Historial de noticias en " + categoria + ":");
                            for (Noticia noticia : historial) {
                                salida.writeUTF("- " + noticia.getContenido());
                            }
                        }
                        break;


                }
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado.");
        }
    }

    private void suscribirse(String categoria) throws IOException {
        boolean categoriaEncontrada = false;
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(categoria)) {
                if (!suscripciones.contains(c)) {
                    suscripciones.add(c);
                    servidor.addObserver(this);  // Suscribir el cliente al servidor
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
