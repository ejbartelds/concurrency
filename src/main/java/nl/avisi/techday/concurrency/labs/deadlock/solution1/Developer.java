package nl.avisi.techday.concurrency.labs.deadlock.solution1;

import java.util.concurrent.Callable;

/**
 * In plaats van altijd eerst links, dan rechts te locken wordt nu eerst op de laptop
 * gelocked met het grootste geheugen en dan op de andere.
 * Vanwege deze ordering in de locks treedt er nu geen deadlock meer op. Want de ordering is nu altijd
 * in dezelfde volgorde (en dat was niet het geval bij left/right)
 */
class Developer implements Callable<String> {
    private final int id;
    private final Laptop first, second;

    public Developer(int id, final Laptop left, final Laptop right) {
        this.id = id;
        
        // Niet per se nodig (vanwege de manier waarop we de memory sizes aanmaken, maar ja, voor de volledigheid.
        if (left.compareTo(right) == 0) throw new IllegalArgumentException("Laptops moeten elk een unieke memory size hebben!");
        
        first = (left.compareTo(right) > 0) ? left : right;
        second = (left.compareTo(right) > 0) ? right : left;
    }

    @Override
    public String call() {
        for (int i = 0; i < 1000; i++) {
            useLaptop();
            startProgramming();
        }
        return "Java is fun";
    }

    public void useLaptop() {
        synchronized (first) {
            synchronized (second) {
                System.out.printf("(%d) Ik pak een laptop%n", id);
            }
        }
    }

    public void startProgramming() {
        System.out.printf("(%d) Lekker programmeren%n", id);
    }
}