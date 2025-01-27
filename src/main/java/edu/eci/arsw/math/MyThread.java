package edu.eci.arsw.math;

public class MyThread extends Thread {
    private int start;
    private int count;
    private byte[] result;

    public MyThread(int start, int count) {
        this.start = start;
        this.count = count;
    }

    @Override
    public void run() {
        // Calcular los dígitos usando PiDigits
        result = PiDigits.getDigits(start, count);
    }

    public byte[] getResult() {
        return result;
    }

    public static void main(String[] args) throws InterruptedException{
        int start = 0;
        int count = 1000;

        MyThread hilo = new MyThread(start, count);
        hilo.start();

        hilo.join();

        // Obtener y convertir los resultados a hexadecimal
        byte[] result = hilo.getResult();
        String hexResult = bytesToHex(result);

        // Imprimir los primeros 100 caracteres del resultado como ejemplo
        System.out.println("Primeros 100 caracteres de PI en hexadecimal:");
        System.out.println(hexResult.substring(0, 100));
        
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<hexChars.length;i=i+2){
            //sb.append(hexChars[i]);
            sb.append(hexChars[i+1]);            
        }
        return sb.toString();
    }
}
