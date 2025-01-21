import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class Cliente extends JFrame {
    private JTextArea areaNoticias;
    private JTextField tfCategoria;
    private Socket socket;
    private DataOutputStream salidaDatos;
    private DataInputStream entradaDatos;

    public Cliente(String host, int puerto) {
        super("Cliente de Noticias");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(200, 100);

        areaNoticias = new JTextArea();
        areaNoticias.setEditable(false);
        JScrollPane scrollNoticias = new JScrollPane(areaNoticias);

        tfCategoria = new JTextField();
        JButton btnSuscribir = new JButton("Suscribirse");

        btnSuscribir.addActionListener(e -> {
            String categoria = tfCategoria.getText().trim();
            if (!categoria.isEmpty()) {
                try {
                    salidaDatos.writeUTF("SUBSCRIBE " + categoria);
                    salidaDatos.flush();
                    areaNoticias.append("Suscrito a la categoría: " + categoria + "\n");
                } catch (IOException ex) {
                    areaNoticias.append("Error al intentar suscribirse.\n");
                    ex.printStackTrace();
                }
            } else {
                areaNoticias.append("Por favor ingrese una categoría válida.\n");
            }
        });

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(scrollNoticias, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(tfCategoria, BorderLayout.CENTER);
        panel.add(btnSuscribir, BorderLayout.EAST);

        container.add(panel, BorderLayout.SOUTH);

        try {
            socket = new Socket(host, puerto);
            salidaDatos = new DataOutputStream(socket.getOutputStream());
            entradaDatos = new DataInputStream(socket.getInputStream());

            new Thread(() -> {
                try {
                    while (true) {
                        String noticia = entradaDatos.readUTF();
                        // Actualiza la interfaz gráfica de manera segura
                        SwingUtilities.invokeLater(() -> {
                            areaNoticias.append(noticia + "\n");
                        });
                    }
                } catch (IOException e) {
                    areaNoticias.append("Error al recibir las noticias.\n");
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException ex) {
            areaNoticias.append("No se pudo conectar al servidor.\n");
            ex.printStackTrace();
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new Cliente("localhost", 6969);
    }
}
