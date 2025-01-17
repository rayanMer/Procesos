import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class EscanerPuertos {
    private final String ip;
    private final int startPort;
    private final int endPort;
    private final Map<Integer, String> results;

    public EscanerPuertos(String ip, int startPort, int endPort) {
        this.ip = ip;
        this.startPort = startPort;
        this.endPort = endPort;
        this.results = new HashMap<>();
    }

    public void empezarEscaner() {
        for (int port = startPort; port <= endPort; port++) {
            try (Socket socket = new Socket(ip, port)) {
                results.put(port, "Abierto");
            } catch (IOException e) {
                results.put(port, "Cerrado");
            }
        }
    }

    public Map<Integer, String> getResults() {
        return results;
    }
}
