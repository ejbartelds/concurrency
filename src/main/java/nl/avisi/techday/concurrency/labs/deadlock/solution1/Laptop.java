package nl.avisi.techday.concurrency.labs.deadlock.solution1;

/**
 * Laptops worden door Developers gebruikt om te  programmeren.
 * Laptops hebben een memory size, wat we gebruiken om een global locking order af te dwingen
 *
 * We gebruiken de Laptops zelf als objects om op te synchronizen.
 */
class Laptop implements Comparable<Laptop> {
    
    private final int memory;
    public Laptop(int memory) {
        this.memory = memory;
    }
    
    @Override
    public int compareTo(Laptop other) {
        return Integer.compare(this.memory, other.memory);
    }

    @Override
    public int hashCode() {
        return memory;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Laptop other) {
            return this.compareTo(other) == 0;
        }
        return false;
    }    
}