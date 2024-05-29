package nl.avisi.techday.concurrency.labs.deadlock.exercise;

/**
 * Launcher om te testen of de Techday deadlock optreedt.  
 * 
 * Mogelijk moet je dit een paar keer runnen om de deadlock tegen te komen.
 */
class Main {
    public static void main(String[] args) throws InterruptedException {
        final ConcurrencyTechday concurrencyTechday = new ConcurrencyTechday(5);
        concurrencyTechday.run();
        System.out.println("Geen deadlock opgetreden!");
    }
}