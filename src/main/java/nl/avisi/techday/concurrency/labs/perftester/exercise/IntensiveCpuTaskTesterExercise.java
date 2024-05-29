package nl.avisi.techday.concurrency.labs.perftester.exercise;

import static java.lang.Math.atan;
import static java.lang.Math.cbrt;
import static java.lang.Math.tan;


class IntensiveCpuTaskTesterExercise {

    private static Runnable cpuIntensiveTask = () -> {
        // Domme maar dure berekening
        for (int i=0; i<100000; i++) {
            double d = tan(atan(tan(atan(tan(atan(tan(atan(tan(atan(123456789.123456789))))))))));
            cbrt(d);
        }
    };


    public long timeTasks(int nThreads, final Runnable task) {

        long start = System.nanoTime();
        
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(task::run);
            // De thread begint meteen zijn taak uit te voeren terwijl andere threads nog moeten starten.
            t.start();
        }

        long end = System.nanoTime();
        return end-start;
    }

    public static void main(String[] args) {
        System.out.println("START");
        final var harness = new IntensiveCpuTaskTesterExercise();
        try {
            final long elapsedTimeNanos = harness.timeTasks(10, cpuIntensiveTask);
            double elapsedTimeInSecond = (double) elapsedTimeNanos / 1_000_000_000;
            System.out.println("DURATION: " + elapsedTimeInSecond);
        } finally {
            System.out.println("END");
        }
    }
}