import javax.swing.*;

public class GUI extends JFrame {
    private JButton btnSinHilos;
    private JPanel main;
    private JButton btnConHilos;
    private JTextField textField1;
    private JProgressBar pbEjecucion;
    private JFrame contexto;
    public GUI(){
        construccion();
        botonSinHilos();
        botonConHilos();

    }

    private void botonConHilos() {
        btnConHilos.addActionListener(event -> {
            TareaParalelizada t = new TareaParalelizada(this.pbEjecucion,1,2000);
            try{
                t.execute();

               // String resultado=t.get();
                /*JOptionPane.showMessageDialog(null,resultado,"Suma",JOptionPane.INFORMATION_MESSAGE);*/
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            }

        });
    }

    private void botonSinHilos() {
        btnSinHilos.addActionListener(event -> {
            int suma=0;
            pbEjecucion.setValue(0);
            for (int i = 0; i < 2000; i++) {
                suma=suma+i;
                pbEjecucion.setValue(suma);
                try {
                    Thread.sleep(1);
                    System.out.println(suma);
                } catch (InterruptedException e) {
                    System.err.println("hilo interrumpido");
                }
            }
        });
    }

    private void construccion() {
        setContentPane(main);
        setSize(600,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
