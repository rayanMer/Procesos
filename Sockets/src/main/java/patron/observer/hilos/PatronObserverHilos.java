package patron.observer.hilos;

public class PatronObserverHilos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TareaObserver observador = new TareaObserver();
        TareaObservable observable = new TareaObservable();

	observable.addObserver(observador);

	Thread hilo = new Thread(observable);

	hilo.start();

    }
    
}
