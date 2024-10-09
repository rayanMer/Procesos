public class AppSumador {
    public static void main(String[] args) {
        if (args.length<1){
            System.err.println("Numero de parametros invalido.");
            System.exit(-8);// -8 Numero de parametros incorrecto

        }
        double suma = 0;
        try {
            for (int i=0;i< args.length;i++){
                suma += Double.parseDouble(args[i]);
            }
        } catch (NumberFormatException e) {
            System.err.println("Formato numerico incorrecto");
            System.exit(-9);//-9 Formato numerico incorrecto
        }
        System.out.println(suma);
    }
}
