package org.example.ProcesoRunTime;


import java.io.IOException;


public class MainRunTime {

    public static void main(String[] args) throws IOException {
        try {
            Process pSecundario = Runtime.getRuntime().exec("ping -c 5 google.com");
            LectorSalidaEstandar lector = new LectorSalidaEstandar(pSecundario.getInputStream());
            lector.ejecutarLectura();
            int fin = pSecundario.waitFor();
            if (fin==0){
                System.out.println(lector.getSalida());}
            else{
                System.err.println("Error...");
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("IOException");
        }


    }
}
