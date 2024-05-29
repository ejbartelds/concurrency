package nl.avisi.techday.concurrency.labs.queue.exercise;

import java.util.Random;

class BoundedQueueTest {

    private static final int CAPACITY = 100;
    private BoundedQueue<Integer> queue = new BoundedQueue<>(CAPACITY);

    private volatile boolean cancel = false;
    
    public void runTasks(int nThreads, int sleepSeconds) throws InterruptedException {
        Thread[] threads = new Thread[nThreads];

        for (int i = 0; i < nThreads; i++) {
            threads[i] = new Thread(() -> {
                final var random = new Random();
                final var tid = Thread.currentThread().threadId();
                final int value = (int) (10 * tid + random.nextInt(5));
                final boolean doPut = random.nextBoolean();
                while (!cancel) {
                    try {
                        if (doPut) {
                            queue.put(value);
                        } else {
                            queue.take();
                        }

                        final int count = queue.getCount();
                        if (count > CAPACITY || count < 0) {
                            // Dit zou NOOIT mogen voorkomen
                            System.out.println("ERROR! tid=" + tid + ", count=" + count);
                            break;
                        }

                        Thread.yield();

                    } catch (OverflowException | UnderflowException e) {
                        // Dit is geen "echte" exceptie, verwacht gedrag. Doe er dus niks mee. 
                    }
                }
            });
        }

        for (int i = 0; i < nThreads; i++) {
            threads[i].start();
        }

        Thread.sleep(sleepSeconds*1000L);
        cancel = true;  // Dit werkt want is volatile

        for (int i = 0; i < nThreads; i++) {
            threads[i].join(); // Wachten maar
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("START");
        final var harness = new BoundedQueueTest();
        try {
            harness.runTasks(8, 10);
            System.out.println("DONE");
        } catch (InterruptedException e) {
            System.out.println("INTERRUPTED");
            throw e;
        } finally {
            System.out.println("END");
        }
    }
}