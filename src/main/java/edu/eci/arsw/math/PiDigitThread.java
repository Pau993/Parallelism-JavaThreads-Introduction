package edu.eci.arsw.math;

public class PiDigitThread extends Thread {
    private final int start;
    private final int count;
    private final byte[] digits;

    public PiDigitThread(int start, int count) {
        this.start = start;
        this.count = count;
        this.digits = new byte[count];
    }

    @Override
    public void run() {
        double sum = 0;

        for (int i = 0; i < count; i++) {
            int currentIndex = start + i;

            if (i % PiDigits.DigitsPerSum == 0) {
                sum = 4 * PiDigits.sum(1, currentIndex)
                        - 2 * PiDigits.sum(4, currentIndex)
                        - PiDigits.sum(5, currentIndex)
                        - PiDigits.sum(6, currentIndex);
            }

            sum = 16 * (sum - Math.floor(sum));
            digits[i] = (byte) sum;
        }
    }

    public byte[] getDigits() {
        return digits;
    }
}
