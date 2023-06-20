import java.util.Queue;
import java.util.Random;

public class Productor implements Runnable {
    private final Queue<Integer> buffer;
    private final int maxSize;
    private final Object lock; // Objeto de bloqueo compartido
    private boolean consumidorTerminado; // Variable para controlar el estado de consumo

    public Productor(Queue<Integer> buffer, int maxSize, Object lock) {
        this.buffer = buffer;
        this.maxSize = maxSize;
        this.lock = lock;
        this.consumidorTerminado = false;
    }

    public void setConsumidorTerminado(boolean consumidorTerminado) {
        this.consumidorTerminado = consumidorTerminado;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                while (!consumidorTerminado || buffer.size() == maxSize) {
                    try {
                        if (!consumidorTerminado) {
                            System.out.println("Esperando a que el consumidor termine de consumir...");
                        } else {
                            System.out.println("Buffer está lleno. El productor está esperando...");
                        }
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int value = produce();
                System.out.println("Producido: " + value);
                consumidorTerminado = false;
                lock.notifyAll();
            }
        }
    }

    private int produce() {
        Random random = new Random();
        int value = random.nextInt(10) + 1; // Generar un nuevo valor entre 1 y 10
        return value;
    }
}
