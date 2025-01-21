import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazServidor extends JFrame {
    private JTextField tfCategoria;
    private JTextArea taContenido;
    private JButton btnEnviar;
    private Servidor servidor;

    public InterfazServidor(Servidor servidor) {
        this.servidor = servidor;

        setTitle("Interfaz de Envío de Noticias");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear componentes
        JLabel lblCategoria = new JLabel("Categoría:");
        tfCategoria = new JTextField(20);
        JLabel lblContenido = new JLabel("Contenido:");
        taContenido = new JTextArea(5, 20);
        taContenido.setLineWrap(true);
        taContenido.setWrapStyleWord(true);
        JScrollPane scrollContenido = new JScrollPane(taContenido);
        btnEnviar = new JButton("Enviar Noticia");

        // Crear layout
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(2, 2, 5, 5));
        panelFormulario.add(lblCategoria);
        panelFormulario.add(tfCategoria);
        panelFormulario.add(lblContenido);
        panelFormulario.add(scrollContenido);

        // Agregar los componentes al contenedor principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(panelFormulario, BorderLayout.CENTER);
        panel.add(btnEnviar, BorderLayout.SOUTH);

        add(panel);

        // Acciones del botón "Enviar Noticia"
        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoria = tfCategoria.getText().trim();
                String contenido = taContenido.getText().trim();

                if (!categoria.isEmpty() && !contenido.isEmpty()) {
                    servidor.publicarNoticia(categoria, contenido);
                    taContenido.setText(""); // Limpiar el área de contenido
                    tfCategoria.setText(""); // Limpiar el campo de categoría
                    JOptionPane.showMessageDialog(null, "Noticia enviada con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese una categoría y contenido.");
                }
            }
        });

        // Mostrar la interfaz
        setVisible(true);
    }

    public static void main(String[] args) {
        // Asegúrate de iniciar la interfaz en el hilo adecuado
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crear servidor y la interfaz gráfica para enviar noticias
                Servidor servidor = new Servidor();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        servidor.iniciar();  // Iniciar el servidor en segundo plano
                    }
                }).start();  // Inicia el servidor en un hilo separado
                new InterfazServidor(servidor);  // Iniciar la interfaz gráfica
            }
        });
    }
}
