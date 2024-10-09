package org.example.ProcesoProcessBuilder;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args)  {
        UtilidadesSistema us = new UtilidadesSistema();
        System.out.println("1-Ejecute un ping solicitando el destino y lo muestre por pantalla. (1 punto)\n" +
                "2-Realice una lista de los archivos y ficheros de a un archivo indicado por el usuario. (1 punto)\n" +
                "3-Lea los procesos del sistema y permita cerrar uno indicando su PID. (1 punto)\n" +
                "4-Ejecute un navegador con la URL indicada. (1 punto)");
        System.out.println("Ingrese el ejercicio que desea realizar: ");
        int opcion = scanner.nextInt();
        if(us.isWindows()) {
            switch (opcion) {
                case 1: {
                    //Ejercicio 1 Ejecute un ping solicitando el destino y lo muestre por pantalla. (1 punto)
                    System.out.println("Introduzca una URL: ");
                    String url = scanner.next();
                    //-c indicamos cuantas N veces queremos que se ejecute el ping
                    try {
                        //Creamos el ProcessBuilder y no lo inicializamos por si queremos modificar el comando
                        ProcessBuilder pb = new ProcessBuilder();
                        pb.command("cmd.exe", "/c", "ping", "-n", "5", url);
                        Process process = pb.start();
                        //Leemos salida de comando y la sacamos por consola
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                        //Nos devuelve el codigo de salida
                        int retorno = process.waitFor();
                        System.out.println("Codigo de retorno Ejercicio 1: " + retorno);

                    } catch (IOException e) {
                        System.out.println(e.getStackTrace());
                    } catch (InterruptedException e) {
                        System.out.println(e.getStackTrace());
                    }
                    break;
                }
                case 2: {
                    {
                        //Ejercicio2: Realice una lista de los archivos y ficheros de a un archivo indicado por el usuario. (1 punto)
                        System.out.println("*****************Listar ficheros y archivos***************");
                        System.out.println("Introduzca una ruta para poder listar los archivos y ficheros");
                        String ruta = scanner.next();
                        try {

                            //Creamos un process builder para poder ejecutar el comando 'dir' para windows
                            ProcessBuilder pb2 = new ProcessBuilder();
                            pb2.command("cmd.exe", "/c", "dir", ruta);
                            //Creamos un proceso para poder empezar a ejecutar
                            Process proceso2 = pb2.start();
                            //Leemos la informacion y la guardamos en una variable
                            BufferedReader br2 = new BufferedReader(new InputStreamReader(proceso2.getInputStream()));
                            String line2;
                            while ((line2 = br2.readLine()) != null) {
                                System.out.println(line2);
                            }
                            int retorno = proceso2.waitFor();
                            System.out.println("Codigo de retorno Ejercicio 2: " + retorno);
                        } catch (InterruptedException e) {
                            System.out.println(e.getStackTrace());
                        } catch (IOException e2) {
                            System.out.println(e2.getStackTrace());
                        }
                    }
                    break;
                }
                case 3:{
                    verProcesos("cmd.exe","/c","tasklist");
                    break;
                }
                case 4:{
                    abrirNavegador();
                    break;
                }
                default:
                    System.err.println("Introduzca una opcion valida: ");
            }
        }else if (us.isLinux()){
            switch (opcion){
                case 1: {
                //Ejercicio 1 Ejecute un ping solicitando el destino y lo muestre por pantalla. (1 punto)
                System.out.println("Introduzca una URL: ");
                String url = scanner.next();
                //-c indicamos cuantas N veces queremos que se ejecute el ping
                try {
                    //Creamos el ProcessBuilder y no lo inicializamos por si queremos modificar el comando
                    ProcessBuilder pb = new ProcessBuilder();
                    pb.command("ping","-c","5",url);
                    Process process = pb.start();
                    //Leemos salida de comando y la sacamos por consola
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    //Nos devuelve el codigo de salida
                    int retorno = process.waitFor();
                    System.out.println("Codigo de retorno Ejercicio 1: " + retorno);

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
            case 2:{
                {
                    //Ejercicio2: Realice una lista de los archivos y ficheros de a un archivo indicado por el usuario. (1 punto)
                    System.out.println("*****************Listar ficheros y archivos***************");
                    System.out.println("Introduzca una ruta para poder listar los archivos y ficheros");
                    String ruta = scanner.next();
                    try {

                        //Creamos un process builder para poder ejecutar el comando 'dir' para windows
                        ProcessBuilder pb2 = new ProcessBuilder();
                        pb2.command( "ls", ruta);
                        //Creamos un proceso para poder empezar a ejecutar
                        Process proceso2 = pb2.start();
                        //Leemos la informacion y la guardamos en una variable
                        BufferedReader br2 = new BufferedReader(new InputStreamReader(proceso2.getInputStream()));
                        String line2;
                        while ((line2 = br2.readLine()) != null) {
                            System.out.println(line2);
                        }
                        int retorno = proceso2.waitFor();
                        System.out.println("Codigo de retorno Ejercicio 2: " + retorno);
                    } catch (InterruptedException e) {
                        System.out.println(e.getStackTrace());
                    } catch (IOException e2) {
                        System.out.println(e2.getStackTrace());
                    }
                }
                break;
            }
            case 3: {
                verProcesos("bash","-c","ps aux");
                break;
            }
            case 4:{
                abrirNavegador();
                break;
            }
                default:
                    System.err.println("INTRODUZCA UNA OPCION VALIDA");
            }
        }

    }
    public static void abrirNavegador(){
        System.out.println("*****************Ejecutar navegador indicando URL***************");
        Desktop d = Desktop.getDesktop();
        System.out.println("Introduzca que quuieres buacar en el navegador: ");
        String buscar = scanner.next();
        try {
            d.browse(new URI("https://",buscar,null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public static void verProcesos(String  sys, String arg1, String arg2){

        System.out.println("*****************Leer los procesos y cerrar indicando PID****************");
        ProcessBuilder pb3 = new ProcessBuilder();

        try {
            pb3.command(sys,arg1,arg2);
            Process proceso3 = pb3.start();
            BufferedReader br3 = new BufferedReader(new InputStreamReader(proceso3.getInputStream()));
            String line3;
            while ((line3 = br3.readLine()) != null) {
                System.out.println(line3);
            }
            int retorno = proceso3.waitFor();

            System.out.println("Introduzca el PID que desea cerrar");
            String PID = scanner.next();
            pb3.command("bash","kill -9",PID);
            Process proceso4 = pb3.start();
            System.out.println("Cerrado proceso con PID: " + PID + " exitosamente");
            proceso4.waitFor();
            System.out.println("Codigo de retorno Ejercicio 4: " + retorno);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        } catch (InterruptedException e2) {
            System.out.println(e2.getStackTrace());
        }

    }
}
