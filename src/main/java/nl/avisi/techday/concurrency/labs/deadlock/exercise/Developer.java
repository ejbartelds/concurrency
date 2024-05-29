package nl.avisi.techday.concurrency.labs.deadlock.exercise;

import java.util.concurrent.Callable;

/**
 * Onze Developer lockt altijd eerst links, dan rechts. Als alle Developers in
 * een cirkel zitten en hun threads roepen allemaal tegelijkertijd "useLaptop()" aan,
 * dan treedt een deadlock op.
 *
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
    public String call() {
        for (int i = 0; i < 1000; i++) {
            useLaptop();
            startProgramming();
        }
        return "Java is leuk";
    }

    public void useLaptop() {
        synchronized (left) {
            synchronized (right) {
                System.out.printf("(%d) Ik pak een laptop%n", id);
            }
        }
    }

    public void startProgramming() {
        System.out.printf("(%d) Lekker programmeren%n", id);
    }
}