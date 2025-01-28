package edu.eci.arsw.math;

import java.util.Arrays;

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


        for (int numThreads : threadConfigurations) {
            runPerformanceTest(numThreads);
        }
    }

    private static void runPerformanceTest(int numThreads) {
        System.out.println("\n--- Experimento con " + numThreads + " hilos ---");


        long startTime = System.nanoTime();


        byte[] piDigits = PiDigits.getDigits(0, TOTAL_DIGITS, numThreads);


        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convertir a milisegundos


        System.out.println("Tiempo de ejecución: " + duration + " ms");
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