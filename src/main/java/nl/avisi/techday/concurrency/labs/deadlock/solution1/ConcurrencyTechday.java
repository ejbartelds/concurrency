package nl.avisi.techday.concurrency.labs.deadlock.solution1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Tijdens de ConcurrencyTechday maken we een aantal Developers aan, we zetten ze in een cirkel
 * en plaatsen Laptops tussen ze in. Er zijn precies evenveel Laptops als er Developers zijn.
 *
 * Dan laten we de Developers runnen in een cached thread pool.
 */
class ConcurrencyTechday {
    private final ExecutorService exec = Executors.newCachedThreadPool();
    private final Laptop[] laptops;
    private final Developer[] developers;

    public ConcurrencyTechday(int developers) {
        
        this.laptops = new Laptop[developers];

        // Maak een randomized list van de getallen 1...<developers>
        // Het belangrijkste is dat deze getallen uniek zijn
        // Deze specifieke manier om dat voor elkaar te krijgen is een onbelangrijk implementatie-detail.
        List<Integer> memoryList = new ArrayList<>();
        for (int i=1; i<developers+1; i++) memoryList.add(i*100);
        Collections.shuffle(memoryList);
        
        for (int i = 0; i < laptops.length; i++) {
            laptops[i] = new Laptop(memoryList.get(i));
        }

        this.developers = new Developer[developers];
        for (int i = 0; i < developers; i++) {
            Laptop left = laptops[i];
            Laptop right = laptops[(i + 1) % developers];
            this.developers[i] = new Developer(i, left, right);
        }
    }

    public void run() throws InterruptedException {
        final CompletionService<String> results = new ExecutorCompletionService<>(exec);
        for (final Developer developer : developers) {
            results.submit(developer);
        }
        System.out.println("Wachten op resultaat");

        int count = developers.length;
        do {
            try {
                System.out.println(results.take().get());
            } catch (final ExecutionException e) {
                e.getCause().printStackTrace();
            }
        } while (--count > 0);

        exec.shutdown();
    }
}