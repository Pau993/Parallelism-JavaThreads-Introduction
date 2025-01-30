package edu.eci.arsw.math;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Clase para realizar pruebas de rendimiento en el cálculo de dígitos de Pi.
 * Permite evaluar el impacto del número de hilos en la ejecución del algoritmo.
 */
public class PiDigitsPerformanceTests {

    /**
     * Número total de dígitos de Pi a calcular.
     */
    private static final int TOTAL_DIGITS = 1_000_0;

    /**
     * Método principal que ejecuta pruebas de rendimiento con diferentes configuraciones de hilos.
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors(); // Obtiene el número de núcleos disponibles
        int[] threadConfigurations = {
                1, // Prueba con un solo hilo
                cores, // Prueba con el número de núcleos del procesador
                cores * 2, // Prueba con el doble de núcleos
                200, // Prueba con 200 hilos
                500 // Prueba con 500 hilos
        };

        Scanner scanner = new Scanner(System.in);

        for (int numThreads : threadConfigurations) {
            System.out.println("\n--- Preparándose para experimento con " + numThreads + " hilos ---");
            System.out.println("Abra jVisualVM y presione Enter cuando esté listo para iniciar la prueba...");
            scanner.nextLine();

            runPerformanceTest(numThreads);

            System.out.println("\nPrueba completada. Revise métricas en jVisualVM.");
            System.out.println("Presione Enter para continuar con la siguiente configuración...");
            scanner.nextLine();
        }

        scanner.close();
    }

    /**
     * Ejecuta la prueba de rendimiento con un número específico de hilos.
     * Mide el tiempo de ejecución y el consumo de memoria.
     * @param numThreads Número de hilos a utilizar en la ejecución.
     */
    private static void runPerformanceTest(int numThreads) {
        System.out.println("\n--- Experimento con " + numThreads + " hilos ---");

        // Forzar recolección de basura antes de la prueba
        System.gc();

        // Medir uso de memoria inicial
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        long startTime = System.nanoTime();
        byte[] piDigits = PiDigits.getDigits(0, TOTAL_DIGITS, numThreads);
        long endTime = System.nanoTime();

        // Medir uso de memoria final
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = memoryAfter - memoryBefore;

        long duration = (endTime - startTime) / 1_000_000; // Convertir a milisegundos

        // Imprimir métricas de rendimiento
        System.out.println("Tiempo de ejecución: " + duration + " ms");
        System.out.println("Memoria utilizada: " + (memoryUsed / 1024 / 1024) + " MB");
        System.out.println("Primeros 10 dígitos hexadecimales: " +
                bytesToHex(Arrays.copyOfRange(piDigits, 0, 10)));
        System.out.println("Últimos 10 dígitos hexadecimales: " +
                bytesToHex(Arrays.copyOfRange(piDigits, piDigits.length - 10, piDigits.length)));
    }

    /**
     * Convierte un arreglo de bytes a una representación en cadena de texto hexadecimal.
     * @param bytes Arreglo de bytes a convertir.
     * @return Representación en formato hexadecimal de los bytes.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = String.format("%02X", b);
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
