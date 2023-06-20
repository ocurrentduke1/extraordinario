import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    public static void main(String[] args) {
        Queue<Integer> buffer = new LinkedList<>();
        int maxSize = 5;
        Object lock = new Object(); // Objeto de bloqueo compartido

        Productor productor = new Productor(buffer, maxSize, lock);
        Consumidor consumidor = new Consumidor(buffer, lock);

        Thread productorThread = new Thread(productor);
        Thread consumidorThread = new Thread(consumidor);

        productorThread.start();
        consumidorThread.start();
    }
}
