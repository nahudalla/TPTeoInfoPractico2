// ESTA MAL

public class Ejercicio6_4C {
    private static final int MINIMO_TIRADAS = 10000;
    private static final double EPSILON = 0.00001;

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.println("Probabilidad "+(i+1)+": " +
                    calcularProbabilidadQueSalganDosConsecutivosIgualesEnEstacionario(
                            i
                    )
            );
        }
    }

    private static double calcularProbabilidadQueSalganDosConsecutivosIgualesEnEstacionario(
            int primerSimbolo
    ) {
        int tiradas = 0;
        int exitos = 0;
        double probabilidadAnterior = -1;
        double probabilidadActual = 0;

        while(
                tiradas < MINIMO_TIRADAS ||
                !converge(probabilidadAnterior, probabilidadActual)
        ) {
            tiradas++;

            int simbolo1 = /*generarPrimerSimbolo()*/ generarSiguienteSimbolo(primerSimbolo);
            int simbolo2 = generarSiguienteSimbolo(simbolo1);

            if(simbolo1 == simbolo2) {
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
        double[] vectorEstacionario = { 1/3.0, 1/2.0, 1/6.0 };
        return generarPrimerSimbolo(vectorEstacionario);
    }

    private static int generarPrimerSimbolo(double[] vectorEstacionario) {
        double random = Math.random();
        double suma = 0;
        for (int i = 0; i < vectorEstacionario.length; i++) {
            suma += vectorEstacionario[i];
            if(suma > random) {
                return i;
            }
        }
        return vectorEstacionario.length - 1;
    }

    private static int generarSiguienteSimbolo(int simboloAnterior) {
        double[][] matrizTransicion = {
//                { 1/2.0, 1/2.0, 0 },
//                { 1/3.0, 1/3.0, 1/3.0 },
//                { 0.0, 1.0, 0.0 }
                  { 1/3.0, 1/2.0, 1/6.0 },
                  { 1/3.0, 1/2.0, 1/6.0 },
                  { 1/3.0, 1/2.0, 1/6.0 }
        };

        double[] columna = matrizTransicion[simboloAnterior];
        return generarPrimerSimbolo(columna);
    }
}
