package nl.avisi.techday.concurrency.labs.deadlock.solution2;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Onze Developer lockt altijd eerst links, dan rechts.
 * We gebruiken "tryLock()", een call die nooit blockeert.
 * Als we de lock niet kunnen krijgen, dan slapen we een korte tijd en proberen het opnieuw.
 * Duurt dit allemaal te lang, dan gooien we een TimeoutException
 */
class Developer implements Callable<String> {
    private final int id;
    private final Laptop left, right;

    public Developer(int id, final Laptop left, final Laptop right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }

    @Override
    public String call() throws Exception {
        for (int i = 0; i < 1000; i++) {
            useLaptop(10, TimeUnit.SECONDS);
            startProgramming();
        }
        return "Java is leuk";
    }

    public void useLaptop(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        long timeoutInNanos = unit.toNanos(timeout);
        long stopTime = System.nanoTime() + timeoutInNanos;
        long sleepTime = ThreadLocalRandom.current().nextLong(100, timeoutInNanos / 50);

        while (true) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            if (left.tryLock()) {
                try {
                    if (right.tryLock()) {
                        try {
                            System.out.printf("(%d) Ik pak een laptop%n", id);
                            return;
                        } finally {
                            right.unlock();
                        }
                    } else {
                        System.out.printf("(%d) Rechter laptop niet beschikbaar%n", id);
                    }
                } finally {
                    left.unlock();
                }
            } else {
                System.out.printf("(%d) Linker laptop niet beschikbaar%n", id);
            }
            if (System.nanoTime() > stopTime) {
                System.out.printf("(%d) Timeout!%n", id);
                throw new TimeoutException();
            }
            
            TimeUnit.NANOSECONDS.sleep(sleepTime);
        }
    }

    public void startProgramming() throws InterruptedException {
        System.out.printf("(%d) Lekker programmeren%n", id);
        Thread.sleep(500);
    }
}