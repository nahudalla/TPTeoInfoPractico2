public class Ejercicio6_2B {
    private static final double EPSILON = 0.00001;
    private static final int MINIMO_TIRADAS = 100000;

    public static void main(String[] args) {
        double[] vectorEstacionario = calcular_vector_estacionario();

        for (double elem : vectorEstacionario) {
            System.out.println(elem);
        }
    }

    private static double[] calcular_vector_estacionario() {
        double[] vectorEstacionarioAnterior = { -1, 0, 0 };
        double[] vectorEstacionarioActual = { 0, 0, 0 };
        int[] frecuenciaSimbolos = { 0, 0, 0 };
        int tiradas = 0;

        int simbolo = generarPrimerSimbolo();
        while(
                tiradas < MINIMO_TIRADAS ||
                !converge(vectorEstacionarioAnterior, vectorEstacionarioActual)
        ) {
            tiradas++;
            simbolo = generarSiguienteSimbolo(simbolo);
            frecuenciaSimbolos[simbolo]++;

            vectorEstacionarioAnterior = vectorEstacionarioActual;
            vectorEstacionarioActual = generarEstacionario(frecuenciaSimbolos, tiradas);
        }

        return vectorEstacionarioActual;
    }

    private static int generarPrimerSimbolo() {
        double[] probabilidades = {1/3.0, 1/3.0, 1/3.0};
        return generarPrimerSimbolo(probabilidades);
    }

    private static int generarPrimerSimbolo(double[] probabilidades) {
        double aleatorio = Math.random();

        double suma = 0;
        for(int i = 0; i < probabilidades.length; ++i) {
            suma += probabilidades[i];
            if(suma > aleatorio) {
                return i;
            }
        }

        return probabilidades.length-1;
    }

    private static boolean converge(
            double[] vectorEstacionarioAnterior,
            double[] vectorEstacionarioActual
    ) {
        for(int i = 0; i < vectorEstacionarioAnterior.length; ++i) {
            double diferencia = Math.abs(
                    vectorEstacionarioActual[i] - vectorEstacionarioAnterior[i]
            );

            if(diferencia > EPSILON) {
                return false;
            }
        }

        return true;
    }

    private static int generarSiguienteSimbolo(int simboloAnterior) {
        double[][] matrizDeTransicion = {
                { 0.0, 1/2.0, 1/2.0 },
                { 1/4.0, 1/2.0, 1/4.0 },
                { 1/4.0, 1/4.0, 1/2.0 }
        };

        double[] columna = matrizDeTransicion[simboloAnterior];
        return generarPrimerSimbolo(columna);
    }

    private static double[] generarEstacionario(
            int[] frecuenciasSimbolos,
            int tiradas
    ) {
        double[] estacionario = new double[frecuenciasSimbolos.length];

        for (int i = 0; i < estacionario.length; i++) {
            estacionario[i] = frecuenciasSimbolos[i] / (double) tiradas;
        }

        return estacionario;
    }
}
