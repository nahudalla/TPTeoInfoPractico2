public class Ejercicio6_3A {
    private static final int MINIMO_TIRADAS = 10000;
    private static final double EPSILON = 0.00001;

    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            System.out.println("Probabilidad para instante "+i+": " + calcularProbabilidadQueSalgaCeroEn(i));
        }
    }

    private static double calcularProbabilidadQueSalgaCeroEn(int instante) {
        int tiradas = 0;
        int exitos = 0;
        double probabilidadAnterior = -1;
        double probabilidadActual = 0;

        while(
                tiradas < MINIMO_TIRADAS ||
                !converge(probabilidadAnterior, probabilidadActual)
        ) {
            tiradas++;

            int simbolo = generarPrimerSimbolo();
            for(int pasos = 0; pasos < instante; ++pasos) {
                simbolo = generarSiguienteSimbolo(simbolo);
            }

            if(simbolo == 0) {
                exitos++;
            }

            probabilidadAnterior = probabilidadActual;
            probabilidadActual = exitos / (double) tiradas;
        }

        return probabilidadActual;
    }

    private static boolean converge(double probabilidadAnterior, double probabilidadActual) {
        double diferencia = Math.abs(probabilidadActual - probabilidadAnterior);
        return diferencia < EPSILON;
    }

    private static int generarPrimerSimbolo() {
        return 0;
    }

    private static int generarSiguienteSimbolo(int simboloAnterior) {
        double[][] matrizTransicion = {
                { 1/4.0, 1/2.0, 1/4.0 },
                { 3/4.0, 1/4.0, 0 },
                { 0, 1/2.0, 1/2.0 }
        };

        double[] columna = matrizTransicion[simboloAnterior];
        double suma = 0;
        double aleatorio = Math.random();
        for (int i = 0; i < columna.length; i++) {
            suma += columna[i];
            if (suma > aleatorio) {
                return i;
            }
        }

        return columna.length - 1;
    }
}
