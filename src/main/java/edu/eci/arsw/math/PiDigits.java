package edu.eci.arsw.math;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;

    
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int numThreads) {
        if (start < 0 || count < 0 || numThreads <= 0) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        int chunkSize = count / numThreads;
        int remainder = count % numThreads;

        PiDigitThread[] threads = new PiDigitThread[numThreads];
        int currentStart = start;
        for (int i = 0; i < numThreads; i++) {
            int currentCount = chunkSize + (i < remainder ? 1 : 0);
            threads[i] = new PiDigitThread(currentStart, currentCount);
            threads[i].start();
            currentStart += currentCount;
        }
        for (PiDigitThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted", e);
            }
        }
        
        byte[] result = new byte[count];
        int index = 0;

        for (PiDigitThread thread : threads) {
            byte[] partialResult = thread.getDigits();
            System.arraycopy(partialResult, 0, result, index, partialResult.length);
            index += partialResult.length;
        }

        return result;
    }

    /// <summary>
    /// Returns the sum of 16^(n - k)/(8 * k + m) from 0 to k.
    /// </summary>
    /// <param name="m"></param>
    /// <param name="n"></param>
    /// <returns></returns>
    public static double sum(int m, int n) {
        // Implementación original
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
    public static int hexExponentModulo(int p, int m) {
        // Implementación original
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
