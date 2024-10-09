package org.example.ProcesoProcessBuilder;

public class UtilidadesSistema {
    String os = System.getProperty("os.name").toLowerCase();

    public boolean isLinux(){
        System.out.println(os);
        return os.contains("linux");
    }

    public boolean isWindows(){
        System.out.println(os);
        return os.contains("win");
    }
}
