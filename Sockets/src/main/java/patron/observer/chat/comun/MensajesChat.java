package patron.observer.chat.comun;

import java.util.Observable;

public class MensajesChat extends Observable{

    private String mensaje;
    
    public MensajesChat(){
    }
    
    public String getMensaje(){
        return mensaje;
    }
    
    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
        // Indica que el mensaje (objeto) ha cambiado dentro del patrón
        this.setChanged();
        // Notifica a los observadores que el mensaje ha cambiado y se lo pasa
        // (Internamente notifyObservers llama al metodo update del observador)
        // Es un patron de diseño para este tipo de aplicaciones. 
        // Notificamos a cada observador pasándole el mensaje
        this.notifyObservers(this.getMensaje());
    }
    
    /**
     * Método que permite añadir observadores de esta clase
     * En este caso no es obligatorio, porque no lo cambiamos
     * y lo heredamos
     */
         /*
	@Override
	public void addObserver(Observer observer) {
		this.observer = observer;
	}
        */
    
    
    
    
}