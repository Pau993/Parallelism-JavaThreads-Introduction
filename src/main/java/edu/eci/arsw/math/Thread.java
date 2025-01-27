package edu.eci.arsw.math;

class PiDigitThread extends Thread {
    private int start;
    private int count;
    private byte[] result;

    public PiDigitThread(int start, int count) {
        this.start = start;
        this.count = count;
    }

    @Override
    public void run() {
        result = PiDigits.getDigits(start, count, 1); // Llamar a getDigits con un solo hilo para esta tarea
    }

    public byte[] getResult() {
        return result;
    }
}
