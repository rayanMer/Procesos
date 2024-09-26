package org.example.PrimerEjercicioTerminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class primerProgramaTerminal {
    public static void main(String[] args) {
        ProcessBuilder proceso = new ProcessBuilder("bash","-c","ls "+args[0]);


        try {
            Process procesoLanzado= proceso.start();
            BufferedReader lector = new BufferedReader(new InputStreamReader(procesoLanzado.getInputStream()));
            procesoLanzado.getInputStream();
            String linea;
            StringBuilder salida = new StringBuilder();
            while((linea= lector.readLine()) != null){
                salida.append(linea+"\n");
                System.out.println(linea);
            }
            System.out.println(salida);

            int res = procesoLanzado.waitFor();
            System.out.println(res);
                     if(res==0){
                        System.out.print("Good");
                  }else{
                  System.out.println("Mal programa");
              }
        }catch (IOException e){
            System.err.println(e.getStackTrace());
        } catch (InterruptedException e2) {
            System.err.println(e2.getStackTrace());
        }
    }

}