import javax.swing.*;


public  class TareaParalelizada extends SwingWorker<String,Integer>{
    private JProgressBar progressBar;
    public TareaParalelizada(JProgressBar pb, int min, int max) {
        this.progressBar=pb;
        progressBar.setMaximum(max);
        progressBar.setMinimum(min);

    }

    @Override
    protected String doInBackground() throws Exception {
        int suma = 0;
        for (int i = 0; i < 2000; i++) {
            suma = suma + i;
            Thread.sleep(10);
            this.progressBar.setValue(i);
        }
        return "Resultado: "+suma;
    }
}
