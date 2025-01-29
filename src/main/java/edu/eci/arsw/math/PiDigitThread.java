package edu.eci.arsw.math;

/**
 * La clase PiDigitThread es un hilo que calcula una secuencia de dígitos de Pi
 * utilizando la fórmula de Bailey-Borwein-Plouffe (BBP).
 */
public class PiDigitThread extends Thread {
    private final int start; // Índice de inicio del cálculo de dígitos
    private final int count; // Cantidad de dígitos a calcular
    private final byte[] digits; // Arreglo que almacena los dígitos calculados

    /**
     * Constructor de la clase PiDigitThread.
     *
     * @param start Índice de inicio del cálculo.
     * @param count Cantidad de dígitos a calcular.
     */
    public PiDigitThread(int start, int count) {
        this.start = start;
        this.count = count;
        this.digits = new byte[count];
    }

    /**
     * Método que ejecuta el cálculo de los dígitos de Pi.
     * Se basa en la fórmula de BBP para calcular los valores en paralelo.
     */
    @Override
    public void run() {
        double sum = 0;

        for (int i = 0; i < count; i++) {
            int currentIndex = start + i;

            // Se recalcula la suma cada cierto número de iteraciones
            if (i % PiDigits.DigitsPerSum == 0) {
                sum = 4 * PiDigits.sum(1, currentIndex)
                        - 2 * PiDigits.sum(4, currentIndex)
                        - PiDigits.sum(5, currentIndex)
                        - PiDigits.sum(6, currentIndex);
            }

            sum = 16 * (sum - Math.floor(sum)); // Se obtiene el siguiente dígito
            digits[i] = (byte) sum;
        }
    }

    /**
     * Obtiene los dígitos calculados de Pi.
     *
     * @return Arreglo de bytes con los dígitos calculados.
     */
    public byte[] getDigits() {
        return digits;
    }
}

