package org.example;

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
    private Servidor servidor;

    public GestionCliente(Socket socket, List<Categoria> categorias, Servidor servidor) {
        this.socket = socket;
        this.categorias = categorias;
        this.suscripciones = new ArrayList<>();
        this.servidor = servidor;
        try {
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // cuando se publica una noticia e ejecuta el update
    @Override
    public void update(Observable o, Object obj) {
        if (obj instanceof Noticia) {
            Noticia noticia = (Noticia) obj;
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
                    case "VER_HISTORIAL":
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
                    servidor.addObserver(this);  // suscribir el cliente al servidor
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

    public Socket getSocket() {
        return socket;
    }

    public List<Categoria> getSuscripciones() {
        return suscripciones;
    }
}
