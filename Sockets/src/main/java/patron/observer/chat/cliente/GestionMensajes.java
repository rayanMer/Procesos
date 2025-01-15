package patron.observer.chat.cliente;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;

public class GestionMensajes extends Thread {
    private DataInputStream entradaDatos;
    private String mensaje;
    private boolean conectado = false;
    private JTextArea mensajesChat;

    public GestionMensajes(DataInputStream entradaDatos, JTextArea mensajesChat) {
        this.entradaDatos = entradaDatos;
        this.mensajesChat = mensajesChat;
    }
    
    
    
    @Override
    public void run(){
        // Bucle infinito que recibe mensajes del servidor, se podría sacar en un uhilo
        
        boolean conectado = true;
        while (conectado) {
            try {
                // Recibimos y actualizamos nuestro cuadro de texto
                mensaje = entradaDatos.readUTF();
                mensajesChat.append(mensaje + System.lineSeparator());
            } catch (IOException ex) {
                System.err.println("Error al leer del stream de entrada: " + ex.getMessage());
                conectado = false;
            } catch (NullPointerException ex) {
                System.err.println("El socket no se creo correctamente. ");
                conectado = false;
            }
        }
        
    }
    
    
    
}
