import java.util.Queue;

public class Consumidor implements Runnable {
    private final Queue<Integer> buffer;
    private final Object lock; // Objeto de bloqueo compartido

    public Consumidor(Queue<Integer> buffer, Object lock) {
        this.buffer = buffer;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                while (buffer.isEmpty() || buffer.peek() == 0) {
                    try {
                        if (buffer.isEmpty()) {
                            System.out.println("Buffer está vacío. El consumidor está esperando...");
                        } else {
                            System.out.println("Valor del buffer es 0. El consumidor está esperando...");
                        }
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int value = buffer.poll();
                value -= 1; // Restar 1 al valor del buffer
                System.out.println("Consumido: " + value);

                if (value > 0) {
                    buffer.add(value); // Agregar el valor actualizado al buffer
                }

                lock.notifyAll();
            }

            try {
                Thread.sleep(1000); // Esperar 1 segundo entre cada consumición
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
