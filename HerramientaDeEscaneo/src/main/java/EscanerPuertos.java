import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class EscanerPuertos {
    private final String ip;
    private final int puertoPrimero;
    private final int puertoUltimo;
    private final Map<Integer, String> resultados;

    public EscanerPuertos(String ip, int puertoPrimero, int puertoUltimo) {
        this.ip = ip;
        this.puertoPrimero = puertoPrimero;
        this.puertoUltimo = puertoUltimo;
        this.resultados = new HashMap<>();
    }

    public void empezarEscaner() {
        for (int puerto = puertoPrimero; puerto <= puertoUltimo; puerto++) {
            try {
                Socket socket = new Socket(ip, puerto);
                resultados.put(puerto, "Abierto");
                socket.close();
            } catch (IOException e) {
                resultados.put(puerto, "Cerrado");
            }
        }
    }

    public Map<Integer, String> getResultados() {
        return resultados;
    }
}
