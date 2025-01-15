package cliente.servidor.refrescos.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Maquina implements Serializable{
     private ArrayList<Refresco> listaRefrescos;
    
    public Maquina(){
        listaRefrescos = new ArrayList<Refresco>();
    }
    
    public synchronized void add(Refresco r){
        listaRefrescos.add(r);
    }
    
    public synchronized Refresco get(int i){
        return listaRefrescos.get(i);
    }
    
    public synchronized Refresco remove(int i){
        return listaRefrescos.remove(i);
    }
    
    public synchronized Maquina removeAll(){
        Maquina lista = new Maquina();
        while(listaRefrescos.size() > 0){
            lista.add(listaRefrescos.remove(0));
        }
        return lista;
    }
    
    public synchronized int size(){
        return listaRefrescos.size();
    }
}
