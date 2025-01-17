import java.util.Scanner;

public class Escaner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Mensaje inicial
        System.out.println("Bienvenido al Escáner de Redes");
        System.out.println("Advertencia: Usa esta herramienta de forma ética y legal.");
        System.out.println("Por favor, introduce la dirección IP a escanear:");

        String ip = scanner.nextLine();
        if (!Utils.isValidIPAddress(ip)) {
            System.out.println("La dirección IP ingresada no es válida.");
            return;
        }

        System.out.println("Introduce el rango de puertos a escanear (por ejemplo, 1-1024):");
        String portRange = scanner.nextLine();
        int startPort = 1, endPort = 1024;

        try {
            String[] parts = portRange.split("-");
            startPort = Integer.parseInt(parts[0]);
            endPort = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            System.out.println("Rango de puertos inválido. Usando el rango por defecto (1-1024).");
        }

        System.out.println("Iniciando el escaneo...");
        EscanerPuertos scannerObj = new EscanerPuertos(ip, startPort, endPort);
        scannerObj.empezarEscaner();

        System.out.println("Guardando resultados...");
        ResultWriter.guardarResultados(scannerObj.getResults(), "resultados.json");
        System.out.println("Escaneo completo. Resultados guardados en 'resultados.json'.");
    }
}
