/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

import java.util.Scanner;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {

    public static void main(String a[]){
        //Declaramos las variables como falsas

        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el valor de A: ");
        int A = sc.nextInt();

        System.out.print("Ingrese el valor de B: ");
        int B = sc.nextInt();

        // Crear y comenzar el hilo
        CountThread thread = new CountThread(A, B);
        thread.start(); // Iniciar el hilo

        CountThread thread1 = new CountThread(0, 99);
        thread1.start();

        CountThread thread2 = new CountThread(99, 199);
        thread2.start();

        CountThread thread3 = new CountThread(200, 299);
        thread.start();

        try {
            thread1.join(); // Esperar a que el hilo termine
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }
        sc.close();
    
    }
}
