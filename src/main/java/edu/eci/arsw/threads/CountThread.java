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
public class CountThread extends Thread{

    private int A;
    private int B;

    public CountThread(int A, int B){
       this.A = A;
       this.B = B;
    }

    @Override
    public void run(){
        if (A > B){
            int intercambio = A;
            A = B;
            B = intercambio;
        }

        System.out.println("Numero entre " + A + " y " + B + ":");
        for(int i = A + 1; i <= B -1; i++){
            System.out.println(i);
        }
        System.out.println("Hilo terminado.");
    }

}