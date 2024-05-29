package nl.avisi.techday.concurrency.labs.deadlock.solution1;

/**
 * Launcher om te testen of de Techday deadlock optreedt.  
 * 
 * Deze implementatie is deadlock-safe.
 */
class Main {
    public static void main(String[] args) throws InterruptedException {
        final ConcurrencyTechday concurrencyTechday = new ConcurrencyTechday(5);
        concurrencyTechday.run();
        System.out.println("Geen deadlock opgetreden!");
    }
}