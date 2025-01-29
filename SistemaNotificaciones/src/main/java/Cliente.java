import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6969);
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
             DataInputStream entrada = new DataInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            new Thread(() -> {
                try {
                    while (true) {
                        System.out.println("Noticia: " + entrada.readUTF());
                    }
                } catch (IOException e) {
                    System.out.println("Desconectado del servidor.");
                }
            }).start();

            while (true) {
                System.out.println("Comandos: SUBSCRIBE <categoria>, UNSUBSCRIBE <categoria>, EXIT, VER_CATEGORIAS");
                String input = scanner.nextLine();
                String[] parts = input.split(" ", 2);
                String comando = parts[0];
                String categoria = parts.length > 1 ? parts[1] : "";

                if (comando.equals("VER_CATEGORIAS")) {
                    // Mostrar las categorías disponibles
                    System.out.println("Categorías disponibles: Tecnologia, Deportes, Entretenimiento");
                } else {
                    JSONObject json = new JSONObject();
                    json.put("comando", comando);
                    json.put("categoria", categoria);
                    salida.writeUTF(json.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
