import java.util.Scanner;

public class Escaner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al Escáner de Redes");
        System.out.println("Advertencia: Usa esta herramienta de forma ética y legal.");
        System.out.println("Por favor, introduce la dirección IP a escanear:");

        String ip = scanner.nextLine();
        if (!Utils.comprobarSiEsValidaLaIp(ip.trim())) {
            System.out.println("La dirección IP ingresada no es válida.");
            return;
        }

        System.out.println("Introduce el rango de puertos a escanear (EJ: 1-5000):");
        String rangoPuerto = scanner.nextLine();
        int puertoPrimero = 1;
        int puertoUltimo = 1024;

        try {
            String[] partes = rangoPuerto.split("-");
            puertoPrimero = Integer.parseInt(partes[0]);
            puertoUltimo = Integer.parseInt(partes[1]);
        } catch (Exception e) {
            System.out.println("Rango de puertos no valido. Usando por defecto rango = 1-1024.");
        }

        System.out.println("***********Iniciando el escaneo************");
        EscanerPuertos escaner = new EscanerPuertos(ip, puertoPrimero, puertoUltimo);
        escaner.empezarEscaner();

        System.out.println("*************Guardando resultados************");
        EscribirResultado.guardarResultados(escaner.getResultados(), "resultados.json");
        System.out.println("Escaneo completo. Resultados guardados en 'resultados.json'.");
    }
}
