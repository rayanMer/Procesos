import java.util.regex.Pattern;

public class Utils {
    private static final String patronIp =
            "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)$";

    public static boolean comprobarSiEsValidaLaIp(String ip) {
        return Pattern.matches(patronIp, ip);
    }
}
