package nl.avisi.techday.concurrency.labs.perftester.solution;

import java.util.concurrent.CountDownLatch;

import static java.lang.Math.atan;
import static java.lang.Math.cbrt;
import static java.lang.Math.tan;

// OPLOSSING: We gebruiken CountDownLatch. Dit is een waitable synchronizer die je initialiseert met een count.
// Roept een thread latch.countdown() aan, dan gaat de count met 1 naar beneden. Wanneer de count op 0 komt dan 
// doet de CountDownLatch een notifyAll(), zodat alle threads die op de latch aan het wachten waren in RUNNABLE 
// status komen. De JVM zal die threads dan schedulen op een CPU/core zodat ze uiteindelijk in status RUNNING komen.
//
// LET OP: wanneer een CountDownLatch eenmaal op 0 is gekomen is ie niet meer bruikbaar; je kan 'm niet resetten
//         of herinitialiseren met een non-zero count.

// We gebruiken in deze oplossing twee CountDownLatches:
//
// - startGate:
//      - Geïnitaliseerd met een count van 1.
//      - De main thread roept eenmalig countdown() aan nadat all workerthreads gestart zijn.
//      - Alle workerthreads krijgen een notificatie en komen uit de WAITING status.
// - endGate:
//      - Geïnitialiseerd met een count gelijk aan het aantal workerthreads. 
//      - Wanneer alle workerthreads gestart zijn gaat de mainthread wachten op deze CountdownLatch.
//      - Wanneer een workerthread klaar is roept ie eenmalig countdown() aan op deze latch.

class IntensiveCpuTaskTesterSolution {

    private static Runnable cpuIntensiveTask = () -> {
        // Domme maar dure berekening
        for (int i=0; i<1_000_000; i++) {
            double d = tan(atan(tan(atan(tan(atan(tan(atan(tan(atan(123456789.123456789))))))))));
            cbrt(d);
        }
    };
    
    
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(() -> {
                    try {
                        startGate.await();  // We slapen totdat de latch op 0 komt.
                        try {
                            task.run();
                        } finally {
                            endGate.countDown(); // Geef aan dat deze workerthread klaar is.
                        }
                    } catch (InterruptedException ignored) { 
                        // Mooi onderwerp voor volgende Techday, vandaag negeren we dit.
                    }
            });
            t.start();
        }
        
        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end-start;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("START");
        final var harness = new IntensiveCpuTaskTesterSolution();
        try {
            final long elapsedTimeNanos = harness.timeTasks(10, cpuIntensiveTask);
            double elapsedTimeInSecond = (double) elapsedTimeNanos / 1_000_000_000;
            System.out.println("DURATION: " + elapsedTimeInSecond);
        } catch (InterruptedException e) {
            System.out.println("INTERRUPTED");
            throw e;
        } finally {
            System.out.println("END");
        }
    }
}