package org.example.ProcesoRunTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LectorSalidaEstandar {
    private BufferedReader br;


    private StringBuilder salida;

    public LectorSalidaEstandar(InputStream inputStream){
        this.br=new BufferedReader(new InputStreamReader(inputStream));
    }
    public void ejecutarLectura() throws IOException {
        salida = new StringBuilder();
        String linea;

        while((linea= this.br.readLine())!=null){
            salida.append(linea+"\n");
        }


    }

    public StringBuilder getSalida() {
        return salida;
    }
}
