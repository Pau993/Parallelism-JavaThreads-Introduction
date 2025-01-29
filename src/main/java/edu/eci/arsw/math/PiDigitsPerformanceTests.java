package edu.eci.arsw.math;

import java.util.Arrays;
import java.util.Scanner;

public class PiDigitsPerformanceTests {
    private static final int TOTAL_DIGITS = 1_000_0;

    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        int[] threadConfigurations = {
                1,
                cores,
                cores * 2,
                200,
                500
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

    private static void runPerformanceTest(int numThreads) {
        System.out.println("\n--- Experimento con " + numThreads + " hilos ---");

        // Forzar recolección de basura antes de la prueba
        System.gc();

        // Métricas de memoria inicial
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        long startTime = System.nanoTime();
        byte[] piDigits = PiDigits.getDigits(0, TOTAL_DIGITS, numThreads);
        long endTime = System.nanoTime();

        // Métricas de memoria final
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = memoryAfter - memoryBefore;

        long duration = (endTime - startTime) / 1_000_000; // Convertir a milisegundos

        // Imprimir métricas detalladas
        System.out.println("Tiempo de ejecución: " + duration + " ms");
        System.out.println("Memoria utilizada: " + (memoryUsed / 1024 / 1024) + " MB");
        System.out.println("Primeros 10 dígitos hexadecimales: " +
                bytesToHex(Arrays.copyOfRange(piDigits, 0, 10)));
        System.out.println("Últimos 10 dígitos hexadecimales: " +
                bytesToHex(Arrays.copyOfRange(piDigits, piDigits.length - 10, piDigits.length)));
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = String.format("%02X", b);
            hexString.append(hex);
        }
        return hexString.toString();
    }
}