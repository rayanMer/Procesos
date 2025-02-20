package org.example;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;

public class ClienteFTP {
    private static final String IP = "127.0.0.1"; // IP del servidor FTP
    private static final int PUERTO = 5000;
    private static final String USUARIO = "admin";
    private static final String CONTRASENA = "admin";

    public static void main(String[] args) {
        FTPClient clienteFtp = new FTPClient();

        try {
            clienteFtp.connect(IP, PUERTO);
            clienteFtp.login(USUARIO, CONTRASENA);
            clienteFtp.enterLocalPassiveMode();
            clienteFtp.setFileType(FTP.BINARY_FILE_TYPE);

            subirArchivo(clienteFtp, "ejemplo.txt");
            listarArchivos(clienteFtp);
            descargarArchivo(clienteFtp, "ejemplo.txt");
            borrarArchivo(clienteFtp, "ejemplo.txt");

            clienteFtp.logout();
            clienteFtp.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void listarArchivos(FTPClient clienteFtp) throws IOException {
        System.out.println("Listando archivos del servidor:");

        FTPFile[] ficheros = clienteFtp.listFiles();
        for (int i = 0; i < ficheros.length; i++) {
            System.out.println(ficheros[i].getName());
        }
    }

    public static void subirArchivo(FTPClient clienteFtp, String nombreArchivo) throws IOException {
        FileInputStream fis = new FileInputStream(nombreArchivo);

        System.out.println("Subiendo archivo '" + nombreArchivo + "' al servidor...");
        if (clienteFtp.storeFile(nombreArchivo, fis)) {
            System.out.println("El archivo se ha subido correctamente.");
        } else {
            System.out.println("Error al subir el archivo.");
        }

        fis.close();
    }

    public static void descargarArchivo(FTPClient clienteFtp, String nombreArchivo) throws IOException {
        File ficheroLocal = new File(nombreArchivo);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(ficheroLocal));

        System.out.println("Descargando archivo '" + nombreArchivo + "' del servidor...");
        if (clienteFtp.retrieveFile(nombreArchivo, os)) {
            System.out.println("El archivo se ha descargado correctamente.");
        } else {
            System.out.println("Error al descargar el archivo.");
        }

        os.close();
    }

    public static void borrarArchivo(FTPClient clienteFtp, String nombreArchivo) throws IOException {
        System.out.println("Eliminando archivo '" + nombreArchivo + "' del servidor...");
        if (clienteFtp.deleteFile(nombreArchivo)) {
            System.out.println("El archivo se ha eliminado correctamente.");
        } else {
            System.out.println("Error al eliminar el archivo.");
        }
    }
}
