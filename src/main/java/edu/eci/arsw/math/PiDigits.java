package edu.eci.arsw.math;

import java.util.ArrayList;
import java.util.List;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;

    
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int cantHilos) {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0){
            throw new RuntimeException("Invalid Interval");
        }

        byte[] digits = new byte[count];
        int digitosHilos = count / cantHilos;
        int digitosSobrantes = count % cantHilos;

        List<MyThread> threads = new ArrayList<>();
        int currentStart = start;

        for(int i =  0; i < cantHilos; i++){
            int calculoHilos = digitosHilos + (i < digitosSobrantes? 1 : 0);
            MyThread hilo = new MyThread(currentStart, calculoHilos);
            threads.add(hilo);
            hilo.start();
            currentStart += digitosHilos;
        }

        for(MyThread hilo : threads){
            try{
                hilo.join();
            }catch(InterruptedException e){
                System.out.println("Error " + e);
            }
        }

        int offset = 0;
        for (MyThread thread : threads) {
            byte[] partialResult = thread.getResult();
            System.arraycopy(partialResult, 0, digits, offset, partialResult.length);
            offset += partialResult.length;
        }

        return digits;

    }

    /// <summary>
    /// Returns the sum of 16^(n - k)/(8 * k + m) from 0 to k.
    /// </summary>
    /// <param name="m"></param>
    /// <param name="n"></param>
    /// <returns></returns>
    private static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < Epsilon) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    /// <summary>
    /// Return 16^p mod m.
    /// </summary>
    /// <param name="p"></param>
    /// <param name="m"></param>
    /// <returns></returns>
    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }

}
