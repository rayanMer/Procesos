package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MainCalculadora {
    public static void main(String[] args) throws IOException {
        //Con esto podemos ver datos como:
        //user.home que es: /home/alumno
        //user.dir:/home/alumno/IdeaProjects/Calculadora
        //user.name:alumno
        System.out.println("****************************************************");
        System.getProperties().forEach((o,o2)-> System.out.println(o+":"+o2));
        System.out.println("****************************************************");

        String path=System.getProperty("user.home")+ File.separator+"jar_files"+File.separator;
        String jarFile="Sumador.jar";
        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String comando = java + " -jar " + path + jarFile;
        Scanner entrada = new Scanner(System.in);
        System.out.println("Instrucciones....");
        String numero="0";
        StringBuilder datosEntrada=new StringBuilder();
        while (!numero.equals("fin")){
            System.out.println("inserte nuevo numero: ");
            numero = entrada.nextLine();
            if (!numero.equals("fin")){
                datosEntrada.append(numero+" ");
            }
        }

        String ejecucion = comando + " " + datosEntrada;
        System.out.println(ejecucion);
        entrada.close();
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("bash","-c",ejecucion);
        try {
            Process sumador=pb.start();
            BufferedReader salidaSumador=new BufferedReader(new InputStreamReader(sumador.getInputStream()));
            BufferedReader errorSumador=new BufferedReader(new InputStreamReader(sumador.getErrorStream()));
            String linea;
            StringBuilder salidaEstandar = new StringBuilder();
            StringBuilder salidaError = new StringBuilder();
            while ((linea= salidaSumador.readLine())!=null){
                salidaEstandar.append(linea+"\n");
            }
            while ((linea= errorSumador.readLine())!=null){
                salidaError.append(linea+"\n");
            }
            int resultadoProceso=sumador.waitFor();
            if (resultadoProceso==0){
                System.out.println(salidaEstandar);
            }else {
                System.err.println(salidaError);
            }
            salidaSumador.close();
            errorSumador.close();

        } catch (IOException e) {
            System.err.println(e);
        } catch (InterruptedException e2) {
            System.err.println(e2);;
        }
    }
}