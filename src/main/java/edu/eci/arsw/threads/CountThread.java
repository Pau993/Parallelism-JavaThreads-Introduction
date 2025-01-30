/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread {
    private int A;
    private int B;

    public CountThread(int A, int B) {
        this.A = A;
        this.B = B;
    }

    /**
     * Método que se ejecuta cuando el hilo inicia.
     * <p>
     * - Verifica si el valor de A es mayor que B y, en caso afirmativo, intercambia sus valores para garantizar
     * que A siempre sea menor o igual que B.
     * - Imprime en consola todos los números enteros comprendidos entre A y B (excluyéndolos).
     * - Al finalizar la impresión, muestra un mensaje indicando que el hilo ha terminado su ejecución.
     */
    @Override
    public void run() {
        // Intercambio de valores si A es mayor que B
        if (A > B) {
            int intercambio = A;
            A = B;
            B = intercambio;
        }

        // Impresión de números en el rango (A, B)
        System.out.println("Números entre " + A + " y " + B + ":");
        for (int i = A + 1; i < B; i++) {
            System.out.println(i);
        }

        // Mensaje de finalización del hilo
        System.out.println("Hilo terminado.");
    }
}